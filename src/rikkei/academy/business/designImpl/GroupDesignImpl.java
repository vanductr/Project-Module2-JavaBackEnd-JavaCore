package rikkei.academy.business.designImpl;

import rikkei.academy.business.design.IGroupDesign;
import rikkei.academy.business.entity.Group;
import rikkei.academy.business.util.IOFile;
import rikkei.academy.business.util.ShopConstants;

import java.util.List;

public class GroupDesignImpl implements IGroupDesign {
    private static List<Group> groupList;

    public GroupDesignImpl() {
        groupList = IOFile.readFromFile(ShopConstants.GROUP_PATH);
    }

    @Override
    public List<Group> getAll() {
        return groupList;
    }

    @Override
    public void save(Group group) {
        if (findById(group.getGroupId()) == null) {
            groupList.add(group);
        } else {
            groupList.set(groupList.indexOf(findById(group.getGroupId())), group);
        }
        IOFile.writeToFile(ShopConstants.GROUP_PATH, groupList);
    }

    @Override
    public Group findById(String groupId) {
        for (Group group : groupList) {
            if (group.getGroupId().equals(groupId)) {
                return group;
            }
        }
        return null;
    }

    @Override
    public void delete(Group group) {
        groupList.remove(group);
        IOFile.writeToFile(ShopConstants.GROUP_PATH, groupList);
    }

    @Override
    public String getNewId() {
        int idMax = 0;
        for (Group group : groupList) {
            int groupId = Integer.parseInt(group.getGroupId().replace("G", "0"));
            if (idMax < groupId) {
                idMax = groupId;
            }
        }
        idMax += 1;
        return "G" + String.format("%03d", idMax);
    }
}
