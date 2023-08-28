package com.qing.erp.member.service;

// Java类

import java.util.Arrays;
// 第三方类
import com.qing.erp.member.entity.UserVO;
import lombok.val;
// Spring类
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.dao.UserDao;
import com.qing.erp.member.entity.UserEntity;

/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Thu Aug 03 16:55:00 CST 2023
 */
@Service
public class UserImpl implements IUserService {
    @Autowired
    private UserDao dao;

    @Autowired
    private UserInfoImpl userInfo;

    @Autowired
    private UserSecurityImpl userSecurity;

    @Autowired
    private UserInventoryImpl userInventory;

    @Autowired
    private UserInventorUniqueImpl userInventorUnique;

    @Autowired
    private UserWalletImpl userWallet;

    @Autowired
    private UserLevelImpl userLevel;

    @Override
    public UserEntity add(UserEntity entity) {
        entity.setId(null);
        return dao.save(entity);
    }

    @Override
    public void remove(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public void removeList(Integer[] ids) {
        dao.deleteAllById(Arrays.asList(ids));
    }

    @Override
    public UserEntity update(UserEntity entity) {
        if (entity.getId() == null) {
            return null;
        }
        return dao.save(entity);
    }

    @Override
    public UserEntity queryOne(Integer id) {
        return id == null ? null : dao.findById(id).orElse(null);
    }

    @Override
    public Page<UserVO> queryPage(Integer page, Integer show) {
        if (page == null || show == null) {
            return null;
        }
        val r = dao.findAll(PageRequest.of(page, show));

        return r.map(user -> {
            // entity to vo
            val vo = new UserVO();
            BeanUtils.copyProperties(user, vo);

            // 联表查询
            val id = vo.getId();
            vo.setInfo(userInfo.findByUserId(id));
            vo.setSecurity(userSecurity.findByUserId(id));
            vo.setInventory(userInventory.findAllByUserId(id));
            vo.setInventorUnique(userInventorUnique.findAllByUserId(id));
            vo.setWallet(userWallet.findByUserId(id));
            vo.setLevel(userLevel.findByUserId(id));
            return vo;
        });
    }

    @Override
    public UserVO login(String account, String password) {
        // TODO: 先根据账号查询出数据
        //   然后将用户传的明文密码进行校验
        //   entity = dao.findByAccount(account);
        //   BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //   passwordEncoder.matches(password, entity.getPassword());

        val entity = dao.findByAccountAndPassword(account, password);
        if (entity == null) {
            return null;
        }
        val vo = new UserVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    // TODO: 注册
    @Override
    public UserVO register(UserEntity user) {
        // 可逆加密与不可逆加密
        /*
        可逆加密：通过密文根据算法可以推算出明文
        不可逆加密：无法推算出明文
        */

        // 彩虹表
        /*
        暴力破解所有值的MD5值存储到数据库，然后存储一个映射表，该映射表称为彩虹表
        */

        // 不可逆加密实现方法：MD5、MD5盐值加密
        /*
        MD5：信息摘要算法，只要一个字节发生变化，结果值就会变化
        -Message Digest algorithm 5，信息摘要算法
        ·压缩性:任意长度的数据，算出的MD5值长度都是固定的。
        ·容易计算:从原数据计算出MD5值很容易。
        ·抗修改性:对原数据进行任何改动，哪怕只修改1个字节，所得到的MD5值都有很大区别。
        ·强抗碰撞:想找到两个不同的数据，使它们具有相同的MD5值，是非常困难的。
        ·不可逆
        【百度网盘秒传功能：计算文件MD5值，如果在百度的服务器里能找到一个一模一样的，就可以使用这个】


        MD5盐值加密：【明文相同，盐值不同密文也不同，增加了彩虹表的难度】
        ·通过生成随机数与MD5生成字符串进行组合
        ·数据库同时存储MD5值与salt值。验证正确性时使用salt进行MD5即可
         */

        // MD5案例
        /*String s= DigestUtils.md5Hex("123456");// e10adc3949ba59abbe56e057f20f883e
        System.out.println(s);*/

        // MD5盐值案例
        /*System.out.println(Md5Crypt.md5Crypt("123456".getBytes()));// 随机盐值，随机MD5值：【盐值：USI.JoH2】【MD5值：$1$USI.JoH2$6hK88QXt9ijipsa/VcnbR0】
        System.out.println(Md5Crypt.md5Crypt("123456".getBytes()));// 随机盐值，随机MD5值：【盐值：tCYQRfTB】【MD5值：$1$tCYQRfTB$thopJ/8DcRSObDwXuKxvn1】
        System.out.println(Md5Crypt.md5Crypt("123456".getBytes(),"$1$123"));// 固定盐值，固定MD5值：【盐值：123】【MD5值：$1$123$7mft0jKnzzvAdU4t0unTG1】
        System.out.println(Md5Crypt.md5Crypt("123456".getBytes(),"$1$123"));// 固定盐值，固定MD5值：【盐值：123】【MD5值：$1$123$7mft0jKnzzvAdU4t0unTG1】
*/

        // 使用spring的MD5+随机盐方法生成密文
        /*BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String encodedPassword1=passwordEncoder.encode("123456");//$2a$10$s0yQ/Tz1aiexGqQGBNgmDuUFpCPjMx8L7TvJ60i9mQSBEmNXbSFEO
        String encodedPassword2=passwordEncoder.encode("123456");//$2a$10$eXhMUTIjoS4cpCB3FRjhlu0QYGwTRgh93CefQSk48hPpvQzzDAvIS
        System.out.println(passwordEncoder.matches("123456",encodedPassword1));// 校验结果true
        System.out.println(passwordEncoder.matches("123456",encodedPassword2));// 校验结果true
        */

        // 校验手机号是否唯一

        // 校验用户名是否唯一

        // 加锁
        /*RLock lock = redissonClient.getLock("LOCK_KEY_REGIST" + user.getPhone());
        try {
            lock.tryLock(30L, TimeUnit.SECONDS);

            // 保存
            MemberEntity entity = new MemberEntity();
            entity.setUsername(user.getUserName());
            entity.setMobile(user.getPhone());
            // 3.1.设置默认等级信息
            MemberLevelEntity level = memberLevelService.getDefaultLevel();
            entity.setLevelId(level.getId());
            // 3.2.设置密码加密存储
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encode = passwordEncoder.encode(user.getPassword());
            entity.setPassword(encode);
            this.baseMapper.insert(entity);
        } finally {
            lock.unlock();
        }*/

        return null;
    }
}