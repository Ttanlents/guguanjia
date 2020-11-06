
let vm=new Vue({
    el:'#fill-main-content',
    data:function () {
        return {
            pageInfo:{},
            condition:{},
            isShow:false,
            name:'全部',   //1.绑定的数据
            searchName:'',
            setting:{
                data:{
                    simpleData: {
                        enable:true,
                        idKey: 'id',
                        pIdKey: 'parentId'
                    }
                },
                callback:{
                    onClick: this.onClick   //每个树节点的点击事件
                },
                view:{
                    fontCss: this.fontCss  //定义更新节点后的，的颜色
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
                url:'sysOffice/select',

            }).then(response=>{
                console.log(response.data);
                if (response.data.success){
                    this.nodes=response.data.obj;
                    this.nodes[this.nodes.length]={"id":0,"name":"全部"};
                    $.fn.zTree.init($('#pullDownTreeone'), this.setting, this.nodes); //3.初始化三步骤：(1)挂载树元素
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
            // （2）设置对象（3）自定义一维数组的nodes
        },
        showTree:function (flag) {
            this.isShow=flag;
        },
        onClick:function (event,treeId,treeNode) {
            //把树的回调函数，下载vue的methods中，目的是：取节点的name值绑定到vue
            this.name=treeNode.name;
            this.searchName=treeNode.name;
            //绑定node参数的id
            if (treeNode.id==0){
                this.condition.id=null;
            }else {
                this.condition.id=treeNode.id;
            }
            let treeObj=$.fn.zTree.getZTreeObj("pullDownTreeone") //获取所有节点  二维
            let arrayNodes=	treeObj.transformToArray(treeObj.getNodes())
            for (let i in arrayNodes){
                //清空之前查询高亮的节点
                arrayNodes[i].height=false;
                treeObj.updateNode(arrayNodes[i])
            }
            $('.btn-group').removeClass("open");
        },
        fontCss:function (treeId,treeNode) {
            return treeNode.height?{"color":"red"}:{"color":"black"};
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
        selectPage:function (pageNum=1,pageSize=5) {
            axios({
                url:`workOrder/selectPage/${pageNum}/${pageSize}`,
                params:this.condition
            }).then(response=>{
                this.pageInfo=response.data.obj;
            }).catch(error=>{

            });
        },
        selectAll:function () {
            this.condition={}
            this.name='全部';
            this.selectPage();
        },
        toDetail:function (id) {
            //iframe窗
            layer.obj = id;
            layer.open({
                type: 2,
                title: false,
                area: ['80%', '80%'],
                content: ['workOrder/toDetail'],
                end:()=> { //此处用于演示
                        layer.msg("关闭完成!")
                }
            })
        }
    },
    created:function(){
        this.selectPage();  //获取数据
        this.condition.status='';
    },
    //beforceCreated-中间做一些vue初始化-created（初始化完毕）-中间解析el以及模板-beforeMounted-挂载数据到html-mounted（挂载完毕）
    //beforeMounted是解析
    mounted:function () {
        //vue挂载后再初始化z树，不然init的参数为空
        this.initTree()
    }
});
