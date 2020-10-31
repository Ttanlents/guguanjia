let vm=new Vue({
    el:'.xuboxPageHtml',
    data:{
        demand:{}
    },
    methods:{
        
    },
    created:function () {
        //从父窗口中取值
        this.demand=parent.layer.obj;
        console.log(this.demand)
    }
});