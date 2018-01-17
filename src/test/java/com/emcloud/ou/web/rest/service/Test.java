package com.emcloud.ou.web.rest.service;

import com.emcloud.ou.EmCloudOuApp;
import com.emcloud.ou.domain.Organization;
import com.emcloud.ou.repository.OrganizationRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
/**
 * Test class for the CustomAuditEventRepository class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmCloudOuApp.class)
@Transactional
public class Test {

    @Autowired
    private OrganizationRepository organizationRepository;

//    public static void main(String[] args) {
//        System.out.println("=========");
////        findTree();
//
//    }+

    @org.junit.Test
    public void findTree() {

        int lastLevelNum = 0; // 上一次的层次
        int curLevelNum = 0; // 本次对象的层次
        Map<String, Object> data = new HashMap<String, Object>();
        try {//查询所有菜单
            List<Organization> allMenu = organizationRepository.findAllByCompanyCode("hx001");

            Collections.sort(allMenu, new Comparator<Organization>() {
                @Override
                public int compare(Organization o1, Organization o2) {
                    return o1.getOrgCode().compareTo(o2.getOrgCode());
                }
            });
            StringBuilder sb = new StringBuilder();
            Organization preNav = null;
            for (Organization nav : allMenu) {
                curLevelNum = getLevelNum(nav);
                if (null != preNav) {
                    if (lastLevelNum == curLevelNum) { // 同一层次的
                        sb.append("}, \n");
                    } else if (lastLevelNum > curLevelNum) { // 这次的层次比上次高一层，也即跳到上一层
                        sb.append("} \n");
                        for (int j = curLevelNum; j < lastLevelNum; j++) {
                            sb.append("]} \n");
                            if (j == lastLevelNum - 1) {
                                sb.append(", \n");
                            }
                        }
                    } else {
                        sb.append(" ,\"children\":[ \n");
                        // sb.append( "</li> \n" );
                    }
                }
                sb.append("{ \n");
                sb.append("\"id\"").append(":").append(+ nav.getId()).append(",");
                sb.append("\"text\"").append(":").append(nav.getOrgName()).append(",");
                sb.append("\"orgCode\"").append(":").append(nav.getOrgCode());

                lastLevelNum = curLevelNum;
                preNav = nav;
            }

            sb.append("} \n");
            for (int j = 1; j < curLevelNum; j++) {
                sb.append("]} \n");
            }
            /**
             * 输出构建好的菜单数据。
             *
             */
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private static int getLevelNum(Organization org){
        return org.getOrgCode().length() / 2;
    }
//
//    private static boolean treeLevelUp(Organization preOrg, Organization curOrg) {
//        // 0101 > 02
//        return preOrg.getOrgCode().length() > preOrg.getOrgCode().length();
//    }
//
//    private static boolean treeLevelCur(Organization preOrg, Organization curOrg) {
//        return preOrg.getOrgCode().length() == preOrg.getOrgCode().length();
//    }
//
//    private static boolean treeLevelDown(Organization preOrg, Organization curOrg) {
//        return preOrg.getOrgCode().length() < preOrg.getOrgCode().length();
//    }

//    /**
//     * 获取子节点
//     *
//     * @param id      父节点id
//     * @param allMenu 所有菜单列表
//     * @return 每个根节点下，所有子菜单列表
//     */
//    public static List<Organization> getChild(String id, List<Organization> allMenu) {
//        //子菜单
//        List<Organization> childList = new ArrayList<Organization>();
//        for (Organization nav : allMenu) {
//            if (nav.getParentCode().equals(id)) {
//                childList.add(nav);
//            }
//        }
//        for (Organization nav : childList) {
//        }
//        if (childList.size() == 0) {
//            return new ArrayList<Organization>();
//        }
//        return childList;
//    }


//    public static String getJeasyTreeData(List<FndResource> datas) {
//        StringBuilder sb = new StringBuilder();
//        FndResource resDTO = null;
//        int childCount = 0;
//        int lastLevelNum = 0; // 上一次的层次
//        int curLevelNum = 0; // 本次对象的层次
//
//        sb.append("[ \n");
//
//        for (int i = 0; i < datas.size(); i++) {
//            resDTO = datas.get(i);
//            childCount = resDTO.getChildCount();
//            curLevelNum = resDTO.getLevelNum();
//            if (i > 0) {
//                if (lastLevelNum == curLevelNum) { // 同一层次的
//                    sb.append("}, \n");
//                } else if (lastLevelNum > curLevelNum) { // 这次的层次比上次高一层，也即跳到上一层
//                    sb.append("} \n");
////          for (int j = curLevelNum; j < lastLevelNum; j++) {
////            sb.append("</ul> \n");
////            sb.append("</li> \n");
////          }
//                    for (int j = curLevelNum; j < lastLevelNum; j++) {
//                        sb.append("]} \n");
//                        if (j == lastLevelNum - 1) {
//                            sb.append(", \n");
//                        }
//                    }
//                } else {
//                    // sb.append( "</li> \n" );
//                }
//            }
//            sb.append("{ \n");
//            sb.append(" \"id\": \"" + resDTO.getResId() + "\" , \n ");
//
//            if (childCount > 0) {
//                sb.append(" \"text\": \"" + resDTO.getResName() + "\" , \n ");
//                sb.append(" \"children\" :  [ \n");
//            } else {
//                sb.append(" \"text\": \"" + resDTO.getResName() + "\" \n ");
//            }
//
//            lastLevelNum = curLevelNum;
//        }
//        sb.append("} \n");
//        for (int j = 0; j < curLevelNum ; j++) {
//            sb.append("]} \n");
//        }
//
//        sb.append("] \n");
//
//        System.out.println(sb);
//        return sb.toString();
//    }

}
