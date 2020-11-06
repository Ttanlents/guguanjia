let vm=new Vue({
    el:'.main-content',
    data:function () {
        return {
            pageInfo:{

            },
            name:'全部',   //1.绑定的数据
            searchName:'',
            condition:{
                dataScope:''
            },
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
            nodes:{

            }
        }
    },
    methods: {
        assignmentRole:function(id){
            layer.obj = id;
            layer.open({
                type: 2,
                title: false,
                area: ['80%', '80%'],
                content: ['sysRole/toAssignmentRole'],
                end:()=> { //此处用于演示
                    if (layer.success!=undefined&&layer.success){
                        layer.msg("更新完成!")
                        this.selectPage() //刷星页面
                    }

                }
            })
        },
        toUpdate:function(sysRole){
            layer.obj = sysRole;
            console.log(layer.obj);
            layer.open({
                type: 2,
                title: false,
                area: ['80%', '80%'],
                content: ['sysRole/toUpdate'],
                end:()=> { //此处用于演示
                    if (layer.success!=undefined&&layer.success){
                        layer.msg("更新完成!");
                        this.selectPage() //刷星页面
                    }

                }
            })
        },
        selectPage: function (pageNum = 1, pageSize = 5) {
            axios({
                url: `sysRole/selectPage/${pageNum}/${pageSize}`,
                params: this.condition
            }).then(response => {
                if (response.data.success) {
                    this.pageInfo = response.data.obj;
                } else {
                    layer.msg("查询失败")
                }
            }).catch(error => {
                layer.msg(error.message)
            });
        },
        selectAll:function(){
            this.condition={dataScope:''};
            this.name='全部';
            this.selectPage();
        },
        onClick: function (event, treeId, treeNode) {
            console.log("1111")
            this.name = treeNode.name;
            this.searchName = treeNode.name;
            if (treeNode.id == 0) {
                this.condition.officeId = null;
            } else {
                this.condition.officeId = treeNode.id;
            }
            let treeObj = $.fn.zTree.getZTreeObj("pullDownTreeone") //获取所有节点  二维
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
        },
        initTree: function () {
            axios({
                url: 'sysOffice/select'
            }).then(response => {
                if (response.data.success) {
                    this.nodes = response.data.obj;
                    this.nodes[this.nodes.length] = {"id": 0, "name": "全部"};
                    $.fn.zTree.init($('#pullDownTreeone'), this.setting, this.nodes); //pullDownTreetwo
                    $.fn.zTree.init($('#pullDownTreetwo'), this.setting2, this.nodes);
                }
            }).catch();
        },
        search:function () {
            let treeObj=$.fn.zTree.getZTreeObj("pullDownTreeone") //获取所有节点  二维
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
    },
    created:function () {
        this.selectPage();
    },
    mounted:function () {
        this.initTree();
    }
});