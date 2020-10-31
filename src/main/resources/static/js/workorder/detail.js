let vm=new Vue({
    el:'.main-content',
    data:{
        work:{}
    },
    methods:{
        selectDetail:function () {
            axios({
                url:`workOrder/doDetail/${this.work.id}`,
            }).then(response=>{
                if (response.data.success){
                    console.log(response.data);
                    this.work=response.data.obj;
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        }
    },
    created:function () {
        //从父窗口中取值
        this.work.id=parent.layer.obj;
        this.selectDetail();
    }
});