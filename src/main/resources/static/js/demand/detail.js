let vm=new Vue({
    el:'.main-content',
    data:{
        demand:{}
    },
    methods:{
        
    },
    created:function () {
        //从父窗口中取值
        this.demand=parent.layer.obj;

    }
});