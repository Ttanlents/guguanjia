let vm=new Vue({
    el:'.main-content',
    data:function () {
        return {
            sysUser:{},
            searchName:'',
            setting:{
                data:{
                    simpleData:{
                        enable:true,
                        idKey:'id',
                        pIdKey:'parentId'
                    }
                },
                callback:{
                    onClick: this.onClick   //每个树节点的点击事件
                },
                view:{
                    fontCss: this.fontCss
                }
            },
            nodes:{}
        }
    },
    methods:{
        initTree: function () {
            axios({
                url: 'sysOffice/select'
            }).then(response => {
                if (response.data.success) {
                    this.nodes = response.data.obj;
                    this.nodes[this.nodes.length] = {"id": 0, "name": "全部"};
                    $.fn.zTree.init($('#pullDownTreethree'), this.setting, this.nodes); //pullDownTreetwo
                }
            }).catch();
        },
        doUpdate:function () {
            axios({
                url:'sysUser/doUpdate',
                method:'put',
                data: this.sysUser
            }).then(response=>{
                if (response.data.success){
                    parent.layer.success=response.data.success;
                    let index=parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }else{
                    layer.msg(response.data.msg)
                }
            }).catch(error=>{
                    layer.msg(error.message)
                }
            )
        },
        search:function () {
            let treeObj=$.fn.zTree.getZTreeObj("pullDownTreethree") //获取所有节点  二维
            let arrayNodes=	treeObj.transformToArray(treeObj.getNodes())
            for (let i in arrayNodes){
                //清空之前查询高亮的节点
                arrayNodes[i].height=false;
                treeObj.updateNode(arrayNodes[i])
            }
            //点亮新节点
            let paramFuzzyObj = treeObj.getNodesByParamFuzzy("name",this.searchName,null);//索引指定条件的nodes
            for (let i in paramFuzzyObj){
                //清空之前查询高亮的节点
                paramFuzzyObj[i].height=true;
                treeObj.updateNode(paramFuzzyObj[i])
            }
        },
        onClick: function (event, treeId, treeNode) {
            this.sysUser.officeName = treeNode.name;
            this.searchName = treeNode.name;
            if (treeNode.id == 0) {
                this.sysUser.officeId=null;
            } else {
                this.sysUser.officeId=treeNode.id;
            }
            let treeObj = $.fn.zTree.getZTreeObj("pullDownTreethree") //获取所有节点  二维
            let arrayNodes = treeObj.transformToArray(treeObj.getNodes())
            for (let i in arrayNodes) {
                //清空之前查询高亮的节点
                arrayNodes[i].height = false;
                treeObj.updateNode(arrayNodes[i])
            }
            $('.btn-group').removeClass("open");
        },
        fontCss: function (treeId, treeNode) {
            return treeNode.height ? {"color": "red"} : {"color": "black"};
        }
    },
    created:function () {
       this.sysUser=parent.layer.obj;
    },
    mounted:function () {
        this.initTree();
    }
});