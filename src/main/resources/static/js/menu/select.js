let vm=new Vue({
    el:'.main-content',
    data:function () {
        return {
            nodes:{},
            name:'',
            id:'',
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

                    $.fn.zTree.init($('#select-tree'), this.setting, this.nodes); //3.初始化三步骤：(1)挂载树元素
                }
            }).catch(error=>{
                layer.msg(error.message)
            });


            // （2）设置对象（3）自定义一维数组的nodes
        },
        onClick: function (event, treeId, treeNode) {
            //把树的回调函数，下载vue的methods中，目的是：取节点的name值绑定到vue
            //绑定node参数的id
            if (treeNode.id!=0&&treeNode.id!=this.id) { //如果不是点自己和全部
                //把id和name往回传
                parent.layer.resourceId=treeNode.id;
                parent.layer.resourceName=treeNode.name;
                parent.layer.resourceParentIds=treeNode.parentIds+treeNode.id+',';

                parent.layer.success=true;
                let index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index)
            } else {
                layer.msg("请选择正确的菜单，不能菜单列表和原来的菜单！")
            }
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
            $(`#${tId}_add`).on('click',this.clickSpan)
        },
        removeHoverDom:function(treeId,treeNode){
            let tId=treeNode.tId;
            $(`#${tId}_add`).unbind().remove();
        }
    },
    created:function () {
       this.id=parent.layer.obj;
    },
    mounted:function () {
        this.initTree();
    }
});