let vm=new Vue({
    el:'.main-content',
    data:{
        username:'admin',
        password:'admin',
        code:'',
        render:'kaptcha/render'
    },
    methods:{
        doLogin:function () {
            axios({
                url:'user/doLogin',
                params:{'username':this.username,'password':this.password,'code':this.code}
            }).then(response=>{
                console.log(response.data);
                if (response.data.success){
                    layer.msg(response.data.msg);
                    let map = JSON.stringify(response.data.obj);
                    sessionStorage.setItem("map",map);
                    console.log("开始跳转...")
                    location.href='index.html';
                }else {
                    layer.msg(response.data.msg);
                    this.refreshCode();
                }
            }).catch(error=>{
                error.msg(error.message);
                this.refreshCode();
            });
        },
        refreshCode:function () {
            this.render=`kaptcha/render?`+new Date();
        }
    }
});