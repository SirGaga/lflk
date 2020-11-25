package com.asideal.lflk.utils;

public class LinearTree {

    /**
     * 将id-pid转为tree结构，单一顶层节点，顶层节点的id和pid不能相同
     * @param pidList id-pid对象的列表
     * @return tree结构对象
     */
//    public <M extends BaseTreeEntity> M buildTreeSingleTop(List<? extends BaseTreeEntity> pidList){
//        //以pid为Key进行分组存入Map
//        Map<Integer,List<M>> pidListMap =
//                pidList.stream().collect(Collectors.groupingBy(M::getParentId));
//        pidList.stream().forEach(item->item.setChildren(pidListMap.get(item.getId())));
//        //取出顶层节点的对象，本例顶层节点的"PID"为0,注意是PID
//        return pidListMap.get(0).get(0);
//    }

    /**
     * 将id-pid转为tree结构，多顶层节点，顶层节点的id和pid不能相同
     * @param pidList id-pid对象的列表
     * @return tree结构对象
     */
//    public List<TestEntity> buildTreeMulTop(List<TestEntity> pidList){
//        Map<Integer,List<TestEntity>> pidListMap =
//                pidList.stream().collect(Collectors.groupingBy(TestEntity::getPid));
//        pidList.stream().forEach(item->item.setChildren(pidListMap.get(item.getId())));
//        //返回结果也改为返回顶层节点的list
//        return pidListMap.get(0);
//    }
}
