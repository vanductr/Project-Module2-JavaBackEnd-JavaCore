package rikkei.academy.business.designImpl;

import rikkei.academy.business.entity.Tag;
import rikkei.academy.business.design.ITagDesign;
import rikkei.academy.business.util.IOFile;
import rikkei.academy.business.util.ShopConstants;

import java.util.List;

public class TagDesignImpl implements ITagDesign {
    private static List<Tag> tagList;

    public TagDesignImpl() {
        tagList = IOFile.readFromFile(ShopConstants.TAG_PATH);
    }

    @Override
    public List<Tag> getAll() {
        return tagList;
    }

    @Override
    public void save(Tag tag) {
        if (findById(tag.getTagId()) == null) {
            tagList.add(tag);
            System.out.println("Đã thêm thành công Tag mới.");
        } else {
            tagList.set(tagList.indexOf(findById(tag.getTagId())), tag);
            System.out.println("Đã sửa thành công thông tin Tag");
        }
        IOFile.writeToFile(ShopConstants.TAG_PATH, tagList);
    }

    @Override
    public Tag findById(String tagId) {
        for (Tag tag : tagList) {
            if (tag.getTagId().equals(tagId)) {
                return tag;
            }
        }
        return null;
    }

    @Override
    public void delete(Tag tag) {
        tagList.remove(tag);
        System.out.println("Đã xoá thành công 1 Tag.");
        IOFile.writeToFile(ShopConstants.TAG_PATH, tagList);
    }

    @Override
    public String getNewId() {
        int idMax = 0;
        for (Tag tag : tagList) {
            int tagId = Integer.parseInt(tag.getTagId().replace("T", "0"));
            if (idMax < tagId) {
                idMax = tagId;
            }
        }
        idMax += 1;
        return "T" + String.format("%03d", idMax);
    }
}
