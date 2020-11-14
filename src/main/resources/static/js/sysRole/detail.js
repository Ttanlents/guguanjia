let vm=new Vue({
    el:'.main-content',
    data:{
        sysRole:{},
        resources:{},
        sysUser:{}
    },
    methods:{
        getAuthority:function () {
            axios({
                url:'sysRole/getAuthority',
                params:{id:this.sysRole.id}
            }).then(response=>{
                if (response.data.success){
                    this.resources=response.data.obj;
                }
            }).catch();
        },
        getAssignment:function () {
            axios({
                url:'sysRole/getAssignment',
                params:{id:this.sysRole.id}
            }).then(response=>{
                if (response.data.success){
                    this.sysUser=response.data.obj;
                }
            }).catch();
        }
    },
    created:function () {
        this.sysRole=parent.layer.obj;
        this.getAuthority();
        this.getAssignment();
    }
});