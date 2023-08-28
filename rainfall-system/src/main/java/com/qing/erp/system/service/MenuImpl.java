package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.dao.MenuDao;
import com.qing.erp.system.pojo.MenuEntity;
import com.qing.erp.system.pojo.MenuVo;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuImpl implements IMenuService {
    @Autowired
    private MenuDao dao;

    private static List<MenuVo> tree(List<MenuVo> menuVoList) {
        Map<Integer, MenuVo> map = new HashMap<>();
        for (MenuVo menuVo : menuVoList) {
            map.put(menuVo.getId(), menuVo);
        }
        List<MenuVo> tree = new ArrayList<>();

        for (MenuVo menuVo : menuVoList) {
            if (menuVo.getParentId() != null && map.containsKey(menuVo.getParentId())) {
                MenuVo parent = map.get(menuVo.getParentId());
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(menuVo);
            } else {
                tree.add(menuVo);
            }
        }
        return tree;
    }

    private static List<MenuVo> treeByRecursion(List<MenuVo> list, Integer id){
        val childList = new ArrayList<MenuVo>();

        // 筛选出是id子菜单的菜单出来
        list.stream()
                .filter(v -> id.equals(v.getParentId()))
                .forEach(childList::add);

        // 这里应该返回null的, 但是前端antd的列表组件的children需要一个空列表
        if (childList.size() == 0) {
            return null;
        }

        childList.forEach(v -> {
            // 设置selfPath(url+View.vue)
            v.urlToSelfPath();
            // 设置path(parentUrl + selfParent + View.vue)
            val parentName = list
                    .stream()
                    .filter(j -> j.getParentId() == null)
                    .filter(j -> v.getParentId().equals(j.getId()))
                    .findAny()
                    .map(MenuVo::getUrl);
            parentName.ifPresent(s -> v.setPath(s + "/" + v.getSelfPath()));
            v.setChildren(treeByRecursion(list, v.getId()));
        });

        return childList;
    }

    @Override
    public R add(MenuEntity entity) {
        entity.setId(null);
        dao.save(entity);
        return R.ok();
    }

    @Override
    public R remove(Integer id) {
        if (id == null) {
            return R.error();
        }
        dao.deleteById(id);
        return R.ok();
    }
    @Override
    public R removeList(Integer[] ids) {
        if (ids == null) {
            return R.error();
        }
        dao.deleteAllById(Arrays.asList(ids));
        return R.ok();
    }

    @Override
    public R update(MenuEntity entity) {
        if (entity.getId() == null) {
            return R.error();
        }
        entity.setUpdatedAt(new Date());
        dao.save(entity);
        return R.ok();
    }

    @Override
    public R queryPage(Integer page, Integer show) {
        if (page == null || show == null) {
            return R.error();
        }
        val r = dao.findAll(PageRequest.of(page, show));
        return R.ok().dataAdd("page", r);
    }

    @Override
    public R queryTree(Boolean flag) {
        // 获取数据
        val all =
                flag == null ? dao.findAll() : dao.findAllByFlag(flag);

        // Entity转为VO
        val list = new ArrayList<MenuVo>();
        all.forEach(v -> {
            val menuVo = new MenuVo();
            BeanUtils.copyProperties(v, menuVo);
            list.add(menuVo);
        });

        // 转为Tree: List<MenuVo> tree = tree(list);

        // 转为Tree(递归)
        val res = new ArrayList<MenuVo>();
        list.stream()
                // 过滤出一级菜单
                .filter(v -> v.getParentId() == null)
                // 递归设置子节点
                .forEach(v -> {
                    // 设置selfPath(url+View.vue)
                    v.urlToSelfPath();
                    //
                    v.setChildren(treeByRecursion(list, v.getId()));
                    res.add(v);
                });

        return R.ok().dataAdd("tree", res);
    }
}
