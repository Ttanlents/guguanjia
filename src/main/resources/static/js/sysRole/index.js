let vm=new Vue({
    el:'.main-content',
    data:function () {
        return {
            active:true,
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
            setting2: {      //添加模块的菜单树 和 办公树 运用该树
                data: {
                    simpleData: {
                        enable: true,
                        idKey: 'id',
                        pIdKey: 'parentId'
                    }
                },
                callback: {
                    //onClick: this.onClick   每个树节点的点击事件
                },
                check:{
                    enable: true,
                    chkboxType : {"Y": "p", "N": "s"}
                }
            },
            nodes:{

            },
            resourceTreeObj:{},
            resourceNodes:{},

            officeTreeObj:{},
            officeNodes:{},

            params:{
                role:{},
                resources:[],//当前授权资源列表
                offices:[] //当前授权公司列表
            },

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
        toDetail:function(sysRole){
            layer.obj = sysRole;
            console.log(layer.obj);
            layer.open({
                type: 2,
                title: false,
                area: ['80%', '80%'],
                content: ['sysRole/toDetail'],
                end:()=> { //此处用于演示

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
        initResourceTree: function () {    //角色添加模块 资源树
            axios({
                url: 'sysResource/selectAll'
            }).then(response => {
                if (response.data.success) {
                    this.resourceNodes = response.data.obj;
                    this.resourceNodes[this.resourceNodes.length]={
                        "id": 0,
                        "name": "所有权限",
                        // "checked":true   //设置默认选中
                    };
                    this.resourceTreeObj=$.fn.zTree.init($('#select-treetreeSelectRes'), this.setting2, this.resourceNodes);

                }
            }).catch();
        },
        initOfficeTree: function () {
            axios({
                url: 'sysOffice/select',
            }).then(response => {
                if (response.data.success) {
                    this.officeNodes = response.data.obj;
                    this.officeNodes[this.officeNodes.length]={
                        "id": 0,
                        "name": "所有机构",
                    };
                    this.officeTreeObj=  $.fn.zTree.init($('#select-treetreeSelectOffice'), this.setting2, this.officeNodes);
                    $("#select-treetreeSelectOffice").css("display","inline-block");//设置  显示这颗树
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
        toSelect:function () {
            layer.obj=0;
            layer.open({
                type: 2,
                title: false,
                area: ['80%', '80%'],
                content: ['sysRole/toSelect'],
                end:()=> { //此处用于演示
                    if (layer.success!=undefined&&layer.success){
                        Vue.set(this.params.role,'officeId',layer.officeId);
                        Vue.set(this.params.role,'officeName',layer.officeName);
                        layer.success=false;
                        layer.msg("选择完成!")
                    }

                }
            })
        },
        changeDataScope:function () {//修改访问数据范围处理
            //数据范围由9改为其他
            if(this.officeTreeObj!=''&&this.params.role.dataScope!=9){

                this.officeTreeObj = '';
                //   this.flag = true;
                $("#select-treetreeSelectOffice").css("display","none");//设置隐藏
            }else if(this.params.role.dataScope==9){

                //数据范围从其他改为9
                this.initOfficeTree();//初始化公司树   用于跨机构授权处理
            }
        },
        doInsert:function(){
            let resources=this.resourceTreeObj.getCheckedNodes(true); //更新的时候获取当前被授权列表
            if(resources.length>0&&resources[0].id==0){//第一个资源是全部权限，需要删除
                resources.splice(0,1);
            }
            for (let i = 0; i < resources.length; i++) {
                this.params.resources.push(resources[i].id);   //获取resources  ids
            }
            if(this.officeTreeObj!=''){
                let offices=this.officeTreeObj.getCheckedNodes(true);  //获取当前被选中授权公司
                if(offices.length>0&&offices[0].id==0){//第一个资源是全部权限，需要删除
                    offices.splice(0,1);
                }
                for (let i = 0; i < offices.length; i++) {
                    this.params.offices.push(offices[i].id);   //获取offices  ids
                }
            }
            console.log(this.params)
            axios({
                url:'sysRole/doInsert',
                method:'post',
                data:this.params
            }).then(response=>{
                if (response.data.success){
                    this.active=!this.active;
                    layer.msg('添加完成');
                    let transformNodes = this.officeTreeObj.transformToArray(this.officeTreeObj.getNodes());
                    for (let j = 0; j < transformNodes.length; j++) {
                        transformNodes[j].checked=false;
                    }
                    let transformNodes2 = this.resourceTreeObj.transformToArray(this.resourceTreeObj.getNodes());
                    for (let j = 0; j < transformNodes2.length; j++) {
                        transformNodes2[j].checked=false;
                    }

                }else {
                    this.params.offices=[];
                    this.params.resources=[];
                }
            }).catch(error=>{
                layer.msg(error.message)
                this.params.offices=[];
                this.params.resources=[];
            });

        },
        changeActive:function (a) {
            if (a==1){
                this.active=true;
                debugger
            }else {
                this.active=false;
            }
        }
    },
    created:function () {
        this.selectPage();
    },
    mounted:function () {
        this.initTree();
        this.initResourceTree();

    }
});