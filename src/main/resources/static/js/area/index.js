
let vm=new Vue({
    el:'.main-content',
    data:function () {
        return {
            pageInfo:{},
            condition:{},
            setting:{
                data:{
                    simpleData: {
                        enable:true,
                        idKey: 'id',
                        pIdKey: 'parentId'
                    }
                },
                callback:{
                    onClick: this.onClick,  //每个树节点的点击事件
                    beforeEditName:this.beforeEditName,  //移除默认编辑的效果
                    beforeRemove:this.beforeRemove      //移除默认删除的效果
                },
                view:{
                    fontCss: this.fontCss,  //定义更新节点后的，的颜色
                    showTitle: true,
                    addHoverDom:this.addHoverDom,   //自定义一个树节点隔壁   添加图标的组件
                    removeHoverDom:this.removeHoverDom//自定义移除组件的回调    一定要和上面的配套使用
                },
                edit:{
                    enable:true,
                    renameTitle:"修改",
                    removeTitle:"删除"
                }
            },
            nodes:{

            }
        }
    },
    methods:{
        //2.初始化ZTree
        initTree:function () {
            axios({
                url:'sysArea/selectAll',

            }).then(response=>{
                console.log(response.data);
                if (response.data.success){
                    this.nodes=response.data.obj;
                    this.nodes[this.nodes.length]={"id":0,"name":"区域列表"};
                    console.log(this.nodes)
                    $.fn.zTree.init($('#treeMenu'), this.setting, this.nodes); //3.初始化三步骤：(1)挂载树元素
                }
            }).catch(error=>{
                layer.msg(error.message)
            });


            // （2）设置对象（3）自定义一维数组的nodes
        },
        onClick:function (event,treeId,treeNode) {
            //把树的回调函数，下载vue的methods中，目的是：取节点的name值绑定到vue
            //绑定node参数的id
            this.condition.name='';
            if (treeNode.id==0){
                this.condition.oid=null;
            }else {
                this.condition.sid=treeNode.id;
            }
            this.selectPage();

        },
        beforeEditName:function(treeId,treeNode){
            console.log(treeNode)
            return false;
        },
        beforeRemove:function(treeId,treeNode){
            console.log(treeNode)
            return false;
        },
        clickSpan:function(){
            console.log("点击按钮")
        },
        addHoverDom:function(treeId,treeNode){
            //1.获取节点tid
            let tId=treeNode.tId;
            //2.获取按钮节点元素   节点id为xxx_a
            let elObject=$(`#${tId}_add`);
            if (elObject.length>0){
                return;  //如果已经绑定过了，不再生成添加按钮
                }
            let span=`<span class="button add" id="${tId}_add" title="新增" treenode_add="" style=""></span>`
            $(`#${tId}_a`).append(span);
            //3.绑定点击事件
            $(`#${tId}_add`).on('click',this.clickSpan)
        },
        removeHoverDom:function(treeId,treeNode){
            let tId=treeNode.tId;
            $(`#${tId}_add`).unbind().remove();
        },
        selectPage:function (pageNum=1,pageSize=5) {
            axios({
                url:`sysArea/selectPage/${pageNum}/${pageSize}`,
                params:this.condition
            }).then(response=>{
                this.pageInfo=response.data.obj;
                console.log(this.pageInfo);
            }).catch(error=>{

            });
        },
        selectAll:function () {
            this.condition.sid='';
                this.selectPage();
        },
        toUpdate:function (area) {
                layer.obj = area;
                console.log(layer.obj)
                layer.open({
                    type: 2,
                    title: false,
                    area: ['80%', '80%'],
                    content: ['sysArea/toUpdate'],
                    end:()=> { //此处用于演示

                        if (layer.success!=undefined&&layer.success){
                            layer.msg("更新完成!");
                            this.selectPage() //刷星页面
                            layer.success=false;
                        }

                    }
                })

        }
    },
    created:function(){
        this.selectPage();
    },
    mounted:function () {
        //vue挂载后再初始化，不然init的参数为空
        this.initTree()
    }
});
