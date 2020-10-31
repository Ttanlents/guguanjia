let vm=new Vue({
    el:'.main-content',
    data:{
        statute:{},
        myConfig:{
            //取代 组件默认配置
            UEDITOR_HOME_URL:"static/ueditor/",
            maximumWords: 100000, //最大输入数量
            serverUrl: 'ueditor/controller.php'
        },
        msg:'<font color="red" >张亮麻辣烫</font>'
    },
    /* es6方法语法给默认值，如果有参数就会覆盖默认值*/
    methods:{
        doUpdate:function () {

            axios({
                url:'ueditor/doUpdate',
                method:'put',
                data: this.statute
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

        }
    },
    components:{
        VueUeditorWrap //引入第三方组件
    },
    created:function () {
        //从父窗口中取值
        this.statute=parent.layer.obj;

    },
    mounted:function () {
        jeDate({
            dateCell: '#modifydate',
            format: 'YYYY-MM-DD',
            zIndex: 999999999,
            choosefun:(val)=> {
                console.log(val)
                this.statute.pubDate=val;
            }
        });
    }
});