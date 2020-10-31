let vm=new Vue({
    el:'.main-content',
    data:{
        result:{},
        pageInfo:{},
        condition:{
            type:''
        },
        active:true,
        statute:{type:''},
        myConfig:{
            //取代 组件默认配置
            UEDITOR_HOME_URL:"static/ueditor/",
            maximumWords: 100000, //最大输入数量
            serverUrl: 'ueditor/controller.php'
        }
    },
    methods:{
        selectPage:function (pageNum=1,pageSize=5) {
            axios({
                url:`statute/selectPage/${pageNum}/${pageSize}`,
                params:this.condition
            }).then(response=>{
                console.log(response.data)
                this.pageInfo=response.data.obj;
                this.result=response.data.obj.list;
                console.log("赋值成功！")
            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        toUpdate:function (statute) {
            layer.obj = statute;
            layer.open({
                type: 2,
                title: false,
                area: ['80%', '80%'],
                content: ['statute/toUpdate'],
                end:()=> { //此处用于演示
                    if (layer.success!=undefined&&layer.success){
                        layer.msg("更新完成!")
                        this.selectPage() //刷星页面
                    }

                }
            })
        },
        toAdd:function () {
        console.log(this.statute)
        axios({
            url:'statute/toAdd',
            method:'put',
            data:this.statute
        }).then(response=>{
            if (response.data.success){

                this.active=!this.active;
                this.statute={type:''}
                layer.msg(response.data.msg);
            }
        }).catch(error=>{
            layer.msg(error.message)
        });
        },
        doDelete:function (statute) {
            let title=statute.title;
            let id=statute.id;
            layer.confirm("你确定要删除《"+title+"》吗？", {btn: ['确定', '取消'], title: "提示"}, ()=> {
                axios({
                    url:'statute/doDelete',
                    params:{id:id}
                }).then(response=>{
                    if (response.data.success){
                        layer.msg("删除成功")
                        console.log(this);
                        this.selectPage();
                    }else {
                        layer.msg("删除成功")
                    }
                }).catch(error=>{
                    layer.msg(error.message)
                });

            });

        }
    },
    components:{
        VueUeditorWrap //引入第三方组件
    },
    created : function() {
        this.selectPage();
    },
    mounted:function () {
        jeDate({
            dateCell: '#indate',
            format: 'YYYY-MM-DD',
            zIndex: 999999999,
            choosefun:(val)=> {
                console.log(val);
                this.statute.pubDate=val;
            }
        });
    }

});