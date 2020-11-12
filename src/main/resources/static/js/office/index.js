let vm=new Vue({
    el:'.main-content',
    data:function () {
        return {
            pageInfo:{},
            condition:{},
            result:{},
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
            },
            nodes:{}
        }
    },
    methods:{
        toUpdate:function(office){
            layer.obj=office;
            layer.open({
                type:2,
                title: false,
                area:['80%','80%'],
                content:['sysOffice/toUpdate'],
                end:()=>{
                    if (layer.success!=undefined&&layer.success){
                        layer.msg('更新完成！');
                        this.selectPage();
                        layer.success=false;
                    }
                }
            });
        },
        selectPage:function (pageNum = 1, pageSize = 5) {
            axios({
                url:`sysOffice/selectPage/${pageNum}/${pageSize}`,
                params:this.condition
            }).then(response=>{
                console.log(response.data.obj)
                if (response.data.success){
                    console.log("赋值成功！")
                    this.pageInfo=response.data.obj;
                    this.result=response.data.obj.list;
                }

            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        selectAll:function () {
            this.condition={};
            this.selectPage();
        },
        doDelete:function(id){
            console.log(id);
            layer.confirm(`你确定要删除吗？`,{
                btn:['确定','取消'],
                title:'提示'
            },()=>{
                axios({
                    url:'sysOffice/doDelete',
                    params:{'id':id}
                }).then(response=>{
                    if (response.data.success){
                        layer.msg('删除成功');
                        this.selectPage();
                    }else {
                        layer.msg(response.data.msg);
                    }
                }).catch(error=>{
                    layer.msg(error.message)
                });
            })

        },
        initTree:function () {
            axios({
                url:'sysOffice/select',

            }).then(response=>{
                console.log(response.data);
                if (response.data.success){
                    this.nodes=response.data.obj;
                    this.nodes[this.nodes.length]={"id":0,"name":"全部"};
                    $.fn.zTree.init($('#treeMenu'), this.setting, this.nodes); //3.初始化三步骤：(1)挂载树元素
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
            // （2）设置对象（3）自定义一维数组的nodes
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
        },
        clickSpan:function(){
            console.log(1111)
            layer.open({
                type: 2,
                title: false,
                area: ['80%', '80%'],
                content: ['sysOffice/toAdd'],
                end:()=> { //此处用于演示

                    if (layer.success!=undefined&&layer.success){
                        layer.msg("添加完成!");
                        this.selectPage();//刷星页面
                        layer.success=false;
                        location.href("sysOffice/toIndex");
                    }

                }
            });
        }
    },
    created:function () {
        this.selectPage();
    },
    mounted:function () {
        this.initTree();
    }

});