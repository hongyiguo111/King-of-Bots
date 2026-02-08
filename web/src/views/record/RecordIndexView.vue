<template>
    <ContentField>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th style="width: 100px;">对局Id</th>
                    <th style="width: 130px;">A玩家</th>
                    <th style="width: 50px;"></th>
                    <th style="width: 130px;">B玩家</th>
                    <th>结果</th>
                    <th>对战开始时间</th>
                    <th>录像</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="record in records" :key="record.record.id"> <!--第一个record是外层循环的变量，第二个record是后端的record字段-->
                    <td>{{ record.record.id }}</td>
                    <td>
                        <img :src="record.a_photo" alt=""
                            style="width: 30px; height: 30px; margin-left: 15px; border-radius: 50%; float: left;">
                        <span style="vertical-align: middle">{{ record.a_username }}</span>
                    </td>
                    <td style="">
                        <span style="font-weight: bold; color: #e74c3c; font-size: 16px;">VS</span>
                    </td>
                    <td>
                        <img :src="record.b_photo" alt=""
                            style="width: 30px; height: 30px; margin-left: 15px; border-radius: 50%; float: left;">
                        <span style="vertical-align: middle">{{ record.b_username }}</span>
                    </td>
                    <td>{{ record.result }}</td>
                    <td>{{ record.record.createtime }}</td>
                    <td>
                        <button @click="open_record_content(record.record.id)" type="button"
                            class="btn btn-secondary">查看录像</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <nav aria-label="..." style="display: flex; justify-content: space-between; align-items: center;">
            <div>
                <span style="font-size: 14px; color: #6c757d; line-height: 38px;">共 <strong style="color: #333;">{{
                    total_records }}</strong> 次对局</span>
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
import router from '../../router/index'

export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();
        let records = ref([]);
        let current_page = 1;
        let total_records = ref(0);
        let pages = ref([]);

        const click_page = page => {
            if (page === -2) page = current_page - 1;
            else if (page === -1) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_records.value / 15));

            if (page >= 1 && page <= max_pages) {
                pull_page(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_records.value / 15));
            let new_pages = [];
            for (let i = current_page - 2; i <= current_page + 2; i++) {
                if (i >= 1 && i <= max_pages) {
                    new_pages.push({
                        number: i,
                        is_active: i === current_page ? "active" : "",
                    });
                }
            }
            pages.value = new_pages;
        }

        const pull_page = page => {
            current_page = page;
            $.ajax({
                url: "http://127.0.0.1:3000/record/getlist/",
                data: {
                    page,
                },
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    records.value = resp.records;
                    total_records.value = resp.records_count;

                    update_pages();
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        pull_page(current_page);

        const stringTo2D = map => {
            let g = [];
            for (let i = 0, k = 0; i < 13; i++) {
                let line = [];
                for (let j = 0; j < 14; j++, k++) {
                    if (map[k] === '0') line.push(0);
                    else line.push(1);
                }
                g.push(line);
            }
            return g;
        }

        const open_record_content = (recordId) => {
            for (const record of records.value) {
                if (record.record.id === recordId) {
                    store.commit("updateIsRecord", true);
                    store.commit("updateGame", {
                        map: stringTo2D(record.record.map),
                        a_id: record.record.aid,
                        a_sx: record.record.asx,
                        a_sy: record.record.asy,
                        b_id: record.record.bid,
                        b_sx: record.record.bsx,
                        b_sy: record.record.bsy,
                    });
                    store.commit("updateSteps", {
                        a_steps: record.record.asteps,
                        b_steps: record.record.bsteps
                    });
                    store.commit("updateInfo", {
                        a_photo: record.a_photo,
                        a_username: record.a_username,
                        b_photo: record.b_photo,
                        b_username: record.b_username,
                    });
                    store.commit("updateRecordLoser", record.record.loser);

                    router.push({
                        name: "record_content",
                        params: { recordId },
                    });
                    break;
                }
            }

        };

        return {
            records,
            total_records,
            open_record_content,
            pages,
            click_page,
            update_pages
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