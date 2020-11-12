let vm=new Vue({
    el:'.main-content',
    data:function () {
        return {
            pageInfo:{

            },
            active:true,
            name:'全部',   //1.绑定的数据
            name2:'请选择归属机构',
            searchName:'',
            searchName2:'',
            condition:{officeId:'',roleId:''},  //条件查询专用
            sysUser:{roleId:''},  //添加专用
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
            setting2:{
                data:{
                    simpleData:{
                        enable:true,
                        idKey:'id',
                        pIdKey:'parentId'
                    }
                },
                callback:{
                    onClick: this.onClick2   //每个树节点的点击事件
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
        doSave:function(){
            if (this.sysUser.password!=this.sysUser.repassword){
                layer.msg("两次输入的密码不一致！")
            }else {
                axios({
                    url:'sysUser/doSave',
                    method:'put',
                    data:this.sysUser
                }).then(response=>{
                    if (response.data.success){
                        layer.msg(response.data.msg)
                        //1.清空列表
                        this.sysUser={};
                        //2.切换到左边的ui
                        this.active=!this.active;
                    }else {
                        layer.msg(response.data.msg)
                    }
                }).catch(error=>{

                });
            }
        },
        selectPage: function (pageNum = 1, pageSize = 5) {
            axios({
                url: `sysUser/selectPage/${pageNum}/${pageSize}`,
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
            this.condition={officeId:'',roleId:''};
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
        onClick2: function (event, treeId, treeNode) {
            console.log("222");
            this.name2 = treeNode.name;
            this.searchName2 = treeNode.name;
            if (treeNode.id == 0) {
                this.sysUser.officeId = null;
            } else {
                this.sysUser.officeId = treeNode.id;
            }
            let treeObj = $.fn.zTree.getZTreeObj("pullDownTreetwo") //获取所有节点  二维
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
        search2:function () {
            let treeObj=$.fn.zTree.getZTreeObj("pullDownTreetwo") //获取所有节点  二维
            let arrayNodes=	treeObj.transformToArray(treeObj.getNodes())
            for (let i in arrayNodes){
                //清空之前查询高亮的节点
                arrayNodes[i].height=false;
                treeObj.updateNode(arrayNodes[i])
            }
            //点亮新节点
            let paramFuzzyObj = treeObj.getNodesByParamFuzzy("name",this.searchName2,null);//索引指定条件的nodes
            for (let i in paramFuzzyObj){
                //清空之前查询高亮的节点
                paramFuzzyObj[i].height=true;
                treeObj.updateNode(paramFuzzyObj[i])
            }
        },
        changeActive:function (number) {

            if (number==1){
                this.active=true;
            }else {
                this.active=false;
            }
        },
        toUpdate:function (sysUser) {
            layer.obj = sysUser;
            console.log(layer.obj);
            layer.open({
                type: 2,
                title: false,
                area: ['80%', '80%'],
                content: ['sysUser/toUpdate'],
                end:()=> { //此处用于演示
                    if (layer.success!=undefined&&layer.success){
                        layer.msg("更新完成!");
                        this.selectPage() //刷星页面
                        layer.success=false;
                    }

                }
            })
        },
        doDelete:function (sysUser) {
            layer.confirm(`你确定要删除${sysUser.name}吗？`,{
                btn: ['确定','取消'],
                title:"提示"
            },()=>{
                axios({
                    url:'sysUser/doDelete',
                    params:{id:sysUser.id}
                }).then(response=>{
                    if (response.data.success){
                        layer.msg(response.data.msg);
                        this.selectPage();
                    }else{
                        layer.msg(response.data.msg);
                    }
                }).catch(error=>{
                    layer.msg(error.message);
                });
            })
        }

    },
    created:function () {
        this.selectPage();
    },
    mounted:function () {
        this.initTree();
    }
});