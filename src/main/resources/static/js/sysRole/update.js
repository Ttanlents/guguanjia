let vm=new Vue({
    el:'.main-content',
    data:function () {
        return {
            newRoleId:{},
            treeObj:{},
            myRole:{},
            setting: {
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
            nodes:{},
            _nodes:{},

            officeTreeObj:{},
            officeNodes:{},
            _officeNodes:{},


            params:{
                role:{},
                _resources:[],//原授权资源列表
                resources:[],//当前授权资源列表
                _offices:[], //原授权公司列表
                offices:[] //当前授权公司列表
            },
            flag:false
        }
    }
    ,
    methods:{
        doUpdate:function(){
            /**
             * 1.获取当前被选中授权列表   和 原授权列表
             *      a.查询到已授权列表的时候给原授权列表赋值
             *      b.更新的时候获取当前被授权列表
             *
             * 2.获取当前被选中授权公司   和  原授权公司
             * 3.获取角色信息
             */
            let resources=this.treeObj.getCheckedNodes(true); //更新的时候获取当前被授权列表
            this.params.role=this.myRole;     //获取角色信息
            //this.params.role.roleId=this.newRoleId;

            if(resources.length>0&&resources[0].id==0){//第一个资源是全部权限，需要删除
                resources.splice(0,1);
            }
            for (let i = 0; i < resources.length; i++) {
                this.params.resources.push(resources[i].id);   //获取resources  ids
            }

            if(this.officeTreeObj!=''){
                console.log("this.officeTreeObj");
                console.log(this.officeTreeObj);

                let offices=this.officeTreeObj.getCheckedNodes(true);  //获取当前被选中授权公司
                if(offices.length>0&&offices[0].id==0){//第一个资源是全部权限，需要删除
                    offices.splice(0,1);
                }
                for (let i = 0; i < offices.length; i++) {
                    this.params.offices.push(offices[i].id);   //获取offices  ids
                }
            }else{
                if(this.flag){  //  如果删除标记为true  表示原来有值 现在要删除  office授权
                    this.params.offices = [];
                }else{   //原来没有授权，现在也不用授权
                    this.params.offices = this.params._offices;//新旧一致
                }
            }

            axios({
                url:'sysRole/doUpdate',
                method:'put',
                data:this.params
            }).then(response=>{
                if (response.data.success){
                    let index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.layer.success=true;
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        selectResourceByRoleId:function () {
            axios({
                url:'sysResource/selectResourceByRoleId',
                params:{roleId:this.myRole.id}
            }).then(response=>{
                this._nodes=response.data.obj;  //需要打钩的节点
                let transformNodes = this.treeObj.transformToArray(this.treeObj.getNodes());  //所有的Tree节点，自己附带check属性，所以要转换
                for (let i = 0; i <this._nodes.length ; i++){
                    for (let j = 0; j < transformNodes.length; j++) {
                        if (this._nodes[i].id==transformNodes[j].id){
                            this.params._resources.push(this._nodes[i].id); //a.查询到已授权列表的时候给原授权列表赋值
                            transformNodes[j].checked=true;
                            this.treeObj.updateNode(transformNodes[j]);
                            break;   //该打钩节点找到了，继续下一个节点遍历
                        }
                    }
                }
            }).catch();
        },
        selectOfficeByRoleId:function () {
            axios({
                url:'sysResource/selectOfficeByRoleId',
                params:{roleId:this.myRole.id}
            }).then(response=>{
                this._officeNodes=response.data.obj;  //需要打钩的节点
                let transformNodes = this.officeTreeObj.transformToArray(this.officeTreeObj.getNodes());  //所有的Tree节点，自己附带check属性，所以要转换
                for (let i = 0; i <this._officeNodes.length ; i++){
                    for (let j = 0; j < transformNodes.length; j++) {
                        if (this._officeNodes[i].id==transformNodes[j].id){

                            this.params._offices.push(this._officeNodes[i].id);  //原授权公司

                            transformNodes[j].checked=true;
                            this.officeTreeObj.updateNode(transformNodes[j]);
                            break;   //该打钩节点找到了，继续下一个节点遍历
                        }
                    }
                }

            }).catch();
        },
        initTree: function () {
            axios({
                url: 'sysResource/selectAll'
            }).then(response => {
                if (response.data.success) {
                    this.nodes = response.data.obj;
                    this.nodes[this.nodes.length]={
                        "id": 0,
                        "name": "所有权限",
                        // "checked":true   //设置默认选中
                    };
                  this.treeObj=  $.fn.zTree.init($('#select-treetreeSelectResEdit'), this.setting, this.nodes);
                    this.selectResourceByRoleId();
                }
            }).catch();
        },
        initOfficeTree: function () {
            axios({
                url: 'sysOffice/select',
            }).then(response => {
                if (response.data.success) {
                    this.officeNodes = response.data.obj;
                    this.officeNodes[this.nodes.length]={
                        "id": 0,
                        "name": "所有机构",
                    };
                    this.officeTreeObj=  $.fn.zTree.init($('#select-treetreeSelectOfficeEdit'), this.setting, this.officeNodes);
                    this.selectOfficeByRoleId();
                    this.flag=true;
                    $("#treeSelectOfficeEdit").css("display","inline-block");//设置  显示这颗树
                }
            }).catch();
        },
        toSelect:function () {
            layer.obj=this.myRole.officeId;
            layer.open({
                type: 2,
                title: false,
                area: ['80%', '80%'],
                content: ['sysRole/toSelect'],
                end:()=> { //此处用于演示
                    if (layer.success!=undefined&&layer.success){
                        this.newRoleId=layer.officeId;
                        this.myRole.officeName=layer.officeName;
                        layer.success=false;
                        console.log(this.newRoleId);
                        layer.msg("关闭完成!")
                    }

                }
            })
        },
        changeDataScope:function () {//修改访问数据范围处理
            //数据范围由9改为其他
            if(this.officeTreeObj!=''&&this.myRole.dataScope!=9){
                this.officeTreeObj = '';
             //   this.flag = true;
                $("#treeSelectOfficeEdit").css("display","none");//设置隐藏
            }else if(this.myRole.dataScope==9){
                //数据范围从其他改为9
                this.initOfficeTree();//初始化公司树   用于跨机构授权处理
            }
        }
    },
    created:function () {
        this.myRole=parent.layer.obj;
    },
    mounted:function () {
        this.initTree();
        if(this.myRole.dataScope==9){
            console.log("初始化office树");
            this.initOfficeTree();//如果一开始数据返范围就是9  就初始化公司树   用于跨机构授权处理
        }
    }
});