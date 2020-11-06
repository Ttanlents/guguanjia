let vm = new Vue({
    el: '.main-content',
    data: function () {
        return {
            pageInfo: {},
            name: '',   //1.绑定的数据
            sysUser: {}, //条件 roleId  officeId

            sysUsers: {},  //已选
            ids: [],
            hide: false,

            dxUsers:{},  //待选
            dxIds:[],
            dxHide:false,

            setting: {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: 'id',
                        pIdKey: 'parentId'
                    }
                },
                callback: {
                    onClick: this.onClick   //每个树节点的点击事件
                },
                view: {
                    fontCss: this.fontCss
                }
            },
            nodes: {}
        }
    },
    methods: {
        doDelete:function(){
            axios({
                url:'sysRole/doDelete',
                params:{
                    roleId:this.sysUser.roleId,ids:this.ids+''
                }
            }).then(response=>{
                if (response.data.success){
                    layer.msg(response.data.msg)
                    this.initData();
                    this.selectRole();
                    this.selectNoRole();
                } else {
                    layer.msg(response.data.msg)
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        doInsert:function(){
            axios({
                url:'sysRole/insertNoRole',
                params:{
                    'roleId':this.sysUser.roleId,'userIds':this.dxIds+''
                }
            }).then(response=>{
                if (response.data.success){
                    layer.msg(response.data.msg)
                    this.initData2();
                    this.selectRole();
                    this.selectNoRole();
                } else {
                    layer.msg(response.data.msg)
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        changeBox: function (id) {
            //1.判断该id在sysUsers[]的那个地方,将其属性hide 改为 true ,并且将id存为ids (已选)
            //2.未选  将其id移除  ids数组
            //3.如果ids数组length==0   就将hide改为false
            for (let i in this.sysUsers) {
                if (id == this.sysUsers[i].id) {
                    this.sysUsers[i].checked = !this.sysUsers[i].checked;
                    //说明点击了一下  将check取反
                    if (this.sysUsers[i].checked) {   //true表示已选打钩
                        this.ids.push(id);
                        this.hide = true;  //打开移除按钮
                        return;
                    } else { //false 表示取消选中
                        for(let j in this.ids){
                            if (id==this.ids[j]){
                                this.ids.splice(j,1);
                            }
                        }
                    }
                }
                if (this.ids.length==0){
                    this.hide=false;
                }
            }

        },
        changeBox2: function (id) {
            //1.判断该id在sysUsers[]的那个地方,将其属性hide 改为 true ,并且将id存为ids (已选)
            //2.未选  将其id移除  ids数组
            //3.如果ids数组length==0   就将hide改为false
            for (let i in this.dxUsers) {
                if (id == this.dxUsers[i].id) {
                    this.dxUsers[i].checked = !this.dxUsers[i].checked;
                    //说明点击了一下  将check取反
                    if (this.dxUsers[i].checked) {   //true表示已选打钩
                        this.dxIds.push(id);
                        this.dxHide = true;  //打开移除按钮
                        return;
                    } else { //false 表示取消选中
                        for(let j in this.dxIds){
                            if (id==this.dxIds[j]){
                                this.dxIds.splice(j,1);
                            }
                        }
                    }
                }
                if (this.dxIds.length==0){
                    this.dxHide=false;
                }
            }

        },
        initData:function(){
            this.sysUsers={};
            this.ids=[];
            this.hide=false;
        },
        initData2:function(){
            this.dxUsers={};
            this.dxIds=[];
            this.dxHide=false;
        },
        selectRole: function () {
            axios({
                url: 'sysRole/selectRole',
                params: {'roleId':this.sysUser.roleId}
            }).then(response => {
                if (response.data.success) {
                    console.log(response.data);
                    this.sysUsers = response.data.obj;
                    for (let i in this.sysUsers) {   //绑定check为false
                        this.sysUsers[i].checke = false;
                    }
                }
            }).catch();

        },
        selectNoRole:function(){
            axios({
                url:'sysRole/selectNoRole',
                params:{'roleId':this.sysUser.roleId,'officeId':this.sysUser.officeId}
            }).then(response=>{
                if (response.data.success) {
                    console.log(response.data.obj);
                    this.dxUsers = response.data.obj;
                    for (let i in this.dxUsers) {   //绑定check为false
                        this.dxUsers[i].checke = false;
                    }
                }
            }).catch();
        },
        onClick: function (event, treeId, treeNode) {
            this.name = treeNode.name;
            this.sysUser.officeId = treeNode.id;
            let treeObj = $.fn.zTree.getZTreeObj("treeOffice") //获取所有节点  二维
            let arrayNodes = treeObj.transformToArray(treeObj.getNodes())
            for (let i in arrayNodes) {
                //清空之前查询高亮的节点
                arrayNodes[i].height = false;
                treeObj.updateNode(arrayNodes[i])
            }
            //点亮新节点
            let paramFuzzyObj = treeObj.getNodesByParamFuzzy("name", this.name, null);//索引指定条件的nodes
            for (let i in paramFuzzyObj) {
                //高亮所有指定的name
                paramFuzzyObj[i].height = true;
                treeObj.updateNode(paramFuzzyObj[i])
            }

            this.selectNoRole();


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
                    $.fn.zTree.init($('#treeOffice'), this.setting, this.nodes);
                }
            }).catch();
        },
        search: function () {
            let treeObj = $.fn.zTree.getZTreeObj("treeOffice") //获取所有节点  二维
            let arrayNodes = treeObj.transformToArray(treeObj.getNodes())
            for (let i in arrayNodes) {
                //清空之前查询高亮的节点
                arrayNodes[i].height = false;
                treeObj.updateNode(arrayNodes[i])
            }
            //点亮新节点
            let paramFuzzyObj = treeObj.getNodesByParamFuzzy("name", this.searchName, null);//索引指定条件的nodes
            for (let i in paramFuzzyObj) {
                //清空之前查询高亮的节点
                paramFuzzyObj[i].height = true;
                treeObj.updateNode(paramFuzzyObj[i])
            }
        },
    },
    created: function () {
        this.sysUser.roleId = parent.layer.obj;
        this.selectRole();
    },
    mounted: function () {
        this.initTree();
    }
});