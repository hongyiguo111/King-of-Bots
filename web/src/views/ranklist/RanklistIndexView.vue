<template>
    <ContentField>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th style="width: 300px;">排名</th>
                    <th style="width: 130px;">玩家</th>
                    <th style="width: 300px;">天梯分</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(user, index) in users" :key="user.id">
                    <!-- 
                        排名序号显示：index 来自于 v-for 指令中的第二个参数。
                        例如在父级元素中使用了类似如下的 v-for 循环：
                        
                        <tr v-for="(user, index) in users" :key="user.id">
                        
                        其中 index 是 v-for 提供的当前项的索引（从 0 开始），
                        因此 index + 1 表示从 1 开始的排名序号。
                    -->
                    <td>{{ (current_page - 1) * 15 + index + 1 }}</td>
                    <td>
                        <img :src="user.photo" alt=""
                            style="width: 30px; height: 30px; margin-left: 58px; border-radius: 50%; float: left;">
                        <span style="vertical-align: middle; margin-left: -35px;">{{ user.username }}</span>
                    </td>
                    <td>
                        <span>{{ user.rating }}</span>
                    </td>
                </tr>
            </tbody>
        </table>
        <nav aria-label="..." style="display: flex; justify-content: space-between; align-items: center;">
            <div>
                <span style="font-size: 14px; color: #6c757d; line-height: 38px;">共 <strong style="color: #333;">{{
                    total_users }}</strong> 个玩家</span>
            </div>
            <ul class="pagination" style="margin: 0">
                <li class="page-item" @click="click_page(-2)">
                    <a class="page-link" href="#">前一页</a>
                </li>
                <li :class="'page-item ' + page.is_active" v-for="page in pages" :key="page.number"
                    @click="click_page(page.number)">
                    <a class="page-link" href="#">{{ page.number }}</a>
                </li>
                <li class="page-item" @click="click_page(-1)">
                    <a class="page-link" href="#">后一页</a>
                </li>
            </ul>
        </nav>
    </ContentField>
</template>

<script>
import ContentField from '../../components/ContentField.vue'
import { useStore } from 'vuex';
import { ref } from 'vue';
import $ from 'jquery';

export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();
        let users = ref([]);
        let current_page = ref(1);
        let total_users = ref(0);
        let pages = ref([]);

        const click_page = page => {
            if (page === -2) page = current_page.value - 1;
            else if (page === -1) page = current_page.value + 1;
            let max_pages = parseInt(Math.ceil(total_users.value / 15));

            if (page >= 1 && page <= max_pages) {
                pull_page(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_users.value / 15));
            let new_pages = [];
            for (let i = current_page.value - 2; i <= current_page.value + 2; i++) {
                if (i >= 1 && i <= max_pages) {
                    new_pages.push({
                        number: i,
                        is_active: i === current_page.value ? "active" : "",
                    });
                }
            }
            pages.value = new_pages;
        }

        const pull_page = page => {
            current_page.value = page;
            $.ajax({
                url: "http://127.0.0.1:3000/ranklist/getlist/",
                data: {
                    page,
                },
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    users.value = resp.users;
                    total_users.value = resp.users_count;
                    update_pages();
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        pull_page(current_page.value);

        return {
            users,
            current_page,
            total_users,
            pages,
            click_page
        }
    }
}
</script>

<style scoped>
table td {
    vertical-align: middle;
    text-align: center;
}

table th {
    text-align: center;
}
</style>