let vm=new Vue({
    el:'.main-content',
    data:{
        menu:{

        }
    },
    methods:{
        doInsert:function () {
            axios({
                url:'sysResource/doInsert',
                method:'put',
                data:this.menu
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
        toSelect:function(){
            layer.obj=0;
            layer.open({
                type: 2,
                title:false,
                area:['80%','80%'],
                content:['sysResource/toSelect'],
                end:()=>{
                    if (layer.success!=undefined&&layer.success){
                        this.menu.parentId=layer.resourceId;
                        this.menu.parentName=layer.resourceName;
                        this.menu.parentIds=layer.resourceParentIds;
                        console.log(this.menu)
                        layer.msg('选着完成');
                        layer.success=false;
                    }
                }
            });
        },
        toIcon:function () {
            layer.open({
                type: 2,
                title: false,
                area: ['100%', '100%'],
                content: ['sysResource/toIcon'],
                end:()=> { //此处用于演示
                    if (layer.icon!=undefined&&layer.icon.length>0){
                        this.menu.icon=layer.icon;
                        console.log(this.menu.icon);
                        console.log(this.menu)
                    }

                }
            })
        },
        initMenu:function () {
            axios({
                url: 'sysResource/initMenu'
            }).then(response=> {
                if (response.data.success) {
                    this.menu=response.data.obj;
                    this.menu.parentId=parent.layer.parentNode.id;
                    if (parent.layer.parentNode.parentIds==undefined){
                        this.menu.parentName='';
                        this.menu.parentIds=parent.layer.parentNode.id+','
                    }else {
                        this.menu.parentName=parent.layer.parentNode.name;
                        this.menu.parentIds=parent.layer.parentNode.parentIds+parent.layer.parentNode.id+','
                    }
                }
            }).catch();
        },
        doClose:function () {
            let index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.layer.success=false;
        }
    },
    created:function () {
        this.initMenu();

    }
});