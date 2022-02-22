<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover" class="mgb20" style="height:252px;">
          <div class="user-info">
            <img src="../assets/img/img.jpg" class="user-avator" alt/>
            <div class="user-info-cont">
              <div class="user-info-name">{{ name }}</div>
              <div>{{ role }}</div>
            </div>
          </div>
          <div class="user-info-list">
            上次登录时间：
            <span>{{ operatingTime(lastLoginTime) }}</span>
          </div>
          <div class="user-info-list">
            上次登录地点：
            <span>东莞</span>
          </div>
        </el-card>
        <el-card shadow="hover" style="height:252px;">
          <template #header>
            <div class="clearfix">
              <span>语言详情</span>
            </div>
          </template>
          Vue
          <el-progress :percentage="71.3" color="#42b983"></el-progress>
          JavaScript
          <el-progress :percentage="24.1" color="#f1e05a"></el-progress>
          CSS
          <el-progress :percentage="13.7"></el-progress>
          HTML
          <el-progress :percentage="5.9" color="#f56c6c"></el-progress>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-row :gutter="20" class="mgb20">
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-1">
                <i class="el-icon-coin grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ currentMoney.entryMoney }}</div>
                  <div>今日入库金额</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-2">
                <i class="el-icon-coin grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ currentMoney.deliverMoney }}</div>
                  <div>今日出库金额</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-3">
                <i class="el-icon-money grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ currentMoney.stockMoney }}</div>
                  <div>当前库存金额</div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-card shadow="hover" style="height:403px;">
          <template #header>
            <div class="clearfix">
              <span>待办事项</span>
              <el-button style="float: right; padding: 3px 0" type="text"  @click="handleAdd">添加</el-button>
            </div>
          </template>

          <el-table :show-header="false" :data="todoList" style="width:100%;">
            <el-table-column width="40">
              <template #default="scope">
                <el-checkbox v-model="scope.row.status" @change="todoListStatusChange(scope.row)"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column>
              <template #default="scope">
                <div class="todo-item" :class="{
                                        'todo-item-del': scope.row.status,
                                    }">{{ scope.row.matter }}
                </div>
              </template>
            </el-table-column>
            <el-table-column width="60">
              <template>
                <i class="el-icon-edit"></i>
                <i class="el-icon-delete"></i>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="hover">
          <schart ref="bar" class="schart" canvasId="bar" :options="options"></schart>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <schart ref="line" class="schart" canvasId="line" :options="options2"></schart>
        </el-card>
      </el-col>
    </el-row>
    <!-- 添加、编辑弹出框 -->
    <el-dialog title="添加" v-model="editVisible" width="30%">
      <el-form label-width="90px" :model="form" :rules="rules" ref="formRef">
        <el-form-item label="事项描述" prop="matter">
          <el-input type="textarea" v-model="form.matter"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveMatter">确 定</el-button>
                </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import Schart from "vue-schart";
import {reactive,ref} from "vue";
import moment from "moment";
import {queryToDoMattersList, queryToDoMattersStatus, saveMatterPost,queryEntryDeliverStockToDate,queryEntryDeliverSchart} from "../api/index.js";
import {ElMessage} from "element-plus";

export default {
  name: "dashboard",
  components: {Schart},
  setup() {
    const name = localStorage.getItem("ms_username");
    const lastLoginTime = localStorage.getItem("last_login_time");
    const role = name === "admin" ? "超级管理员" : "普通用户";
    // 表格编辑时弹窗和保存
    const editVisible = ref(false);
    let form = reactive({
      id:null,
      matter:null,
      version:null
    });
    const rules = {
      matter:[
        {
          required: true,
          message: '事项描述不能为空',
          trigger: 'blur',
        },
      ]
    };
    let formRef=ref(null);
    const options = ref({
      type: "bar",
      title: {
        text: "最近一周出入库情况",
      },
      xRorate: 25,
      labels: ["周一", "周二", "周三", "周四", "周五"],
      datasets: [
        {
          label: "入库金额",
          data: [234, 278, 270, 190, 230],
        },
        {
          label: "出库",
          data: [164, 178, 190, 135, 160],
        }
      ],
    });
    const options2 = {
      type: "line",
      title: {
        text: "最近几个月出入库趋势图",
      },
      labels: ["6月", "7月", "8月", "9月", "10月"],
      datasets: [
        {
          label: "入库",
          data: [234, 278, 270, 190, 230],
        },
        {
          label: "出库",
          data: [164, 178, 150, 135, 160],
        }
      ],
    };
    const todoList = ref([]);
    const queryTodoList=()=>{
      queryToDoMattersList({}).then((res)=>{
        todoList.value=res.data;
      })
    }
    queryTodoList();

    const handleAdd=()=>{
      editVisible.value = true;
      form.matter=null;
    };
    const todoListStatusChange=(row)=>{
      queryToDoMattersStatus(row);
    };
    const saveMatter=()=>{
      formRef.value.validate((valid) => {
        if (valid) {
          editVisible.value = false;
          saveMatterPost(form).then((res) => {
            ElMessage.success("保存成功");
            queryTodoList();
          });
        }
      })
    };

    let currentMoney=ref({});
    //查询今日入库出库金额，当前库存
    const queryEntryDeliverStockMoney=()=>{
      queryEntryDeliverStockToDate({}).then((res)=>{
        currentMoney.value=res.data
      })
    };
    queryEntryDeliverStockMoney();
    //查询图标数据
    const queryEntryDeliverSchartData=()=>{
      queryEntryDeliverSchart({}).then((res)=>{
        options.value.labels=res.data.week;
        options.value.datasets[0].data=res.data.weekEntryData;
        options.value.datasets[1].data=res.data.weekDeliverData;
      })
    };
    queryEntryDeliverSchartData();

    const operatingTime = (timeDate) => {
      return moment(timeDate).format('yyyy-MM-DD HH:mm:ss');
    };

    return {
      editVisible,
      form,
      rules,
      formRef,
      saveMatter,
      currentMoney,
      name,
      lastLoginTime,
      options,
      options2,
      todoList,
      role,
      operatingTime,
      todoListStatusChange,
      handleAdd
    };
  },
};
</script>

<style scoped>
.el-row {
  margin-bottom: 20px;
}

.grid-content {
  display: flex;
  align-items: center;
  height: 100px;
}

.grid-cont-right {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: #999;
}

.grid-num {
  font-size: 30px;
  font-weight: bold;
}

.grid-con-icon {
  font-size: 50px;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
  color: #fff;
}

.grid-con-1 .grid-con-icon {
  background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
  background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
  background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
  color: rgb(242, 94, 67);
}

.user-info {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 2px solid #ccc;
  margin-bottom: 20px;
}

.user-avator {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}

.user-info-cont {
  padding-left: 50px;
  flex: 1;
  font-size: 14px;
  color: #999;
}

.user-info-cont div:first-child {
  font-size: 30px;
  color: #222;
}

.user-info-list {
  font-size: 14px;
  color: #999;
  line-height: 25px;
}

.user-info-list span {
  margin-left: 70px;
}

.mgb20 {
  margin-bottom: 20px;
}

.todo-item {
  font-size: 14px;
}

.todo-item-del {
  text-decoration: line-through;
  color: #999;
}

.schart {
  width: 100%;
  height: 300px;
}
</style>
