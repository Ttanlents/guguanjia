let vm=new Vue({
    el:'.main-content',
    data:{
        office:{},
        myConfig:{
            //取代 组件默认配置
            UEDITOR_HOME_URL:"static/ueditor/",
            maximumWords: 100000, //最大输入数量
            serverUrl: 'ueditor/controller.php'
        }
    },
    methods:{
        doUpdate:function () {

            axios({
                url:'sysOffice/doUpdate',
                method:'put',
                data: this.office
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
        toSelect:function () {
            layer.parentId=this.office.parentId;
            console.log(layer.obj);
            layer.open({
                type: 2,
                title: false,
                area: ['100%', '100%'],
                content: ['sysOffice/toSelect'],
                end:()=> { //此处用于演示
                    if (layer.parentName!=undefined&&layer.parentName.length>0){
                        this.office.areaId=layer.aid;
                        this.office.areaName=layer.parentName;

                    }

                }
            })
        }
    },
    created:function () {
        this.office=parent.layer.obj;
    },
    components:{
        VueUeditorWrap //引入第三方组件
    }
});