<template>
    <section id="ipMaskCollect">
        <div class="card-block" @click="dialogFormVisible = true">
            <img class="card-img" src="../assets/img/add.png"/>
            <p>手动导入</p>
        </div>
        <el-dialog title="I P 地 址 采 集" :visible.sync="dialogFormVisible" center>
            <el-form :model="form">
                <el-form-item label="I P 地址：">
                    <el-input v-model="form.ipAddress" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="计算机名：">
                    <el-input v-model="form.computerName" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitFormData">提 交</el-button>
            </div>
        </el-dialog>
    </section>
</template>

<script>
    import API from '../remote/api'
    export default {
        data() {
          return {
              dialogFormVisible: false,
              form: {
                  ipAddress: '',
                  computerName: ''
              }
          }
        },
        methods: {
            submitFormData(){
                let ip   = this.form.ipAddress;
                let name = this.form.computerName;
                let isValid = ip && ip.split('.').length === 4;
                ip.split('.').forEach((value)=>{
                    isValid = isValid && !(value === "" || value === null);
                    isValid = isValid && !isNaN(value);
                });
                if(!isValid){
                    this.$notify.error({title: '错误', message: 'IP格式错误！'});
                    this.form.ipAddress = '';
                    return;
                }
                if(!(name && jQuery.trim(name).length > 0 && name.length < 20)){
                    this.$notify.error({title: '错误', message: '计算机名称不可为空或过长！'});
                    this.form.computerName = '';
                    return;
                }
                let params = {ip: ip, name: name};
                let self = this;
                API.addNewIPAddress(params).then(data=>{
                    self.dialogFormVisible = false;
                    this.$notify.success({title: '信息', message: 'IP地址添加成功'});
                }).catch(ex=>{
                    this.form.ipAddress = '';
                    this.form.computerName = '';
                    this.$notify.error({title: '错误', message: ex.message});
                });
            }
        }
    }
</script>

<style scoped>
    #ipMaskCollect {
        padding: 20px;
    }
    .card-block {
        position: relative;
        display: inline-block;
        width: 200px;
        height: 150px;
        margin-right: 50px;
        text-align: center;
        box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
    }
    .card-img {
        position: absolute;
        width: 100px;
        height: 100px;
        top: 0;
        left: 50%;
        padding: 10px;
        transform: translateX(-50%);
    }
    .card-block p {
        position: absolute;
        width: 100%;
        bottom: 0;
        text-align: center;
        font-size: 18px;
        padding: 10px;
        background-color: #eaeff1;
    }
    .card-block:hover {
        cursor: pointer;
        color: #0088cc;
        box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 12px 40px 0 rgba(0,0,0,0.19);
    }
</style>