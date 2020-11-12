let vm=new Vue({
   el:'.main-content',
   data:function () {
       return {
           nodes:{},
           name:'',
           pageInfo:{},
           setting:{
               data:{
                   simpleData: {
                       enable:true,
                       idKey: 'id',
                       pIdKey: 'parentId'
                   }
               },
               callback:{
                   beforeEditName:this.beforeEditName,  //移除默认编辑的效果
                   beforeRemove:this.beforeRemove      //移除默认删除的效果
               },
               view:{
                   showTitle: true,
                   addHoverDom:this.addHoverDom,   //自定义一个树节点隔壁   添加图标的组件
                   removeHoverDom:this.removeHoverDom//自定义移除组件的回调    一定要和上面的配套使用
               },
               edit:{
                   enable:true,
                   renameTitle:"修改",
                   removeTitle:"删除"
               }
           }
       }
   },
   methods:{
       initTree:function () {
           axios({
               url:'sysResource/selectAll',
           }).then(response=>{
               console.log(response.data);
               if (response.data.success){
                   this.nodes=response.data.obj;
                   this.nodes[this.nodes.length]={"id":0,"name":"菜单列表"};

                   $.fn.zTree.init($('#treeMenu'), this.setting, this.nodes); //3.初始化三步骤：(1)挂载树元素
               }
           }).catch(error=>{
               layer.msg(error.message)
           });


           // （2）设置对象（3）自定义一维数组的nodes
       },
       selectPage:function (pageNum=1,pageSize=5) {
           axios({
               url:`sysResource/selectPage/${pageNum}/${pageSize}`,
               params:{name:this.name}
           }).then(response=>{
               if (response.data.success){
                   this.pageInfo=response.data.obj;
               }
           }).catch(error=>{
                layer.msg(error.message)
           });
       },
       doDelete:function(m){
          layer.confirm(`你确定要删除${m.name}吗？`,{
              btn: ['确定','取消'],
              title:"提示"
          },()=>{
              axios({
                  url:'sysResource/doDelete',
                  method:'put',
                  data:m
              }).then(response=>{
                  if (response.data.success){
                      layer.msg(response.data.msg);
                  }else{
                      layer.msg(response.data.msg);
                  }
              }).catch(error=>{
                  layer.msg(error.message);
              });
          })
       },
       toUpdate:function(m){
           layer.obj=m;
           console.log(layer.obj);
           layer.open({
               type: 2,
               title:false,
               area:['80%','80%'],
               content:['sysResource/toUpdate'],
               end:()=>{
                   if (layer.success!=undefined&&layer.success){
                       layer.msg('更新完成');
                       layer.success=false;
                       /*this.selectPage();*/
                       location.href("sysResource/toIndex")
                   }


               }
           });
       },
       toDetail:function(m){
           layer.obj=m;
           layer.open({
               type:2,
               title:false,
               area:['80%','80%'],
               content:'sysResource/toDetail',
               end:()=>{

               }
           });
       },
       selectAll:function () {
           this.name='';
           this.selectPage()
       },
       clickSpan:function(){

           layer.open({
               type: 2,
               title: false,
               area: ['80%', '80%'],
               content: ['sysResource/toAdd'],
               end:()=> { //此处用于演示

                   if (layer.success!=undefined&&layer.success){
                       layer.msg("添加完成!");
                       this.selectPage();//刷星页面
                       layer.success=false;
                       location.href("sysResource/toIndex");
                   }

               }
           });
       },
       beforeEditName:function(treeId,treeNode){
           return false;
       },
       beforeRemove:function(treeId,treeNode){
           return false;
       },
       addHoverDom:function(treeId,treeNode){
           //1.获取节点tid
           let tId=treeNode.tId;
           //2.获取按钮节点元素   节点id为xxx_a
           let elObject=$(`#${tId}_add`);
           if (elObject.length>0){
               return;  //如果已经绑定过了，不再生成添加按钮
           }
           let span=`<span class="button add" id="${tId}_add" title="新增" treenode_add="" style="" @click=""></span>`
           $(`#${tId}_a`).append(span);
           //3.绑定点击事件
           $(`#${tId}_add`).on('click',this.clickSpan);
           layer.parentNode=treeNode;
       },
       removeHoverDom:function(treeId,treeNode){
           let tId=treeNode.tId;
           $(`#${tId}_add`).unbind().remove();
       }
   },
    created:function () {
        this.selectPage();
    },
    mounted:function () {
        this.initTree();
    }
});