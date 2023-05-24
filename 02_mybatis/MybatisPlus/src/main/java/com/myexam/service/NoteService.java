package com.myexam.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myexam.entity.Note;
import com.myexam.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteService extends ServiceImpl<NoteMapper, Note>  {

    @Resource
    private NoteMapper noteMapper;

    /**
     * 更新某个用户的笔记的parentIds
     */
    public void updateParentIds() {

        System.out.println(("----- UpdateParentIds method test ------"));

        QueryWrapper<Note> queryWrapper = new QueryWrapper();
        queryWrapper.select("*");
        queryWrapper.eq("create_by", "damu");

        List<Note> list = noteMapper.selectList(queryWrapper);
        list.forEach(System.out::println);

        List<Note> updateList = new ArrayList<>();
        Map<Long, Note> allNoteMap = new HashMap();
        for (Note note : list) {
            allNoteMap.put(note.getId(), note);
        }

        for (Note note : list) {
            String parentIds = getParentIds(allNoteMap, note);
            if (StringUtils.isNotBlank(parentIds)) {
                note.setParentIds(parentIds);
                updateList.add(note);
            }
        }
        this.updateBatchById(updateList);
    }

        private String getParentIds(Map<Long, Note> allNoteMap, Note note) {
            if (note.getParentId() == null || note.getParentId().longValue() == 0L) {
                return "0/";
            } else {
                Note parent = allNoteMap.get(note.getParentId());
                if (parent != null) {
                    if (StringUtils.isNotBlank(parent.getParentIds())) {
                        return parent.getParentIds() + note.getParentId() + "/";
                    } else {
                        String parentParentIds = getParentIds(allNoteMap, parent);
                        return parentParentIds==null?null:parentParentIds + note.getParentId() + "/";
                    }
                } else {
                    return null;
                }
            }
        }
}
