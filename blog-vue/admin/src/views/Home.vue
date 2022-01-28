<template>
    <div class="home-container">
      <div class="card-container">
        <div class="card-item">
          <div class="card-title"><i class="fa fa-user" style="color: #409EFF"/> 总用户数</div>
          <div class="split-line"></div>
          <div class="primary-data">2,723</div>
          <div class="compare-data">较昨日: <i class="fa fa-arrow-up" style="color: #52C41A"/>  193</div>
        </div>

        <div class="card-item">
          <div class="card-title"><i class="fa fa-laptop" style="color: #F5222D"/> 实时在线</div>
          <div class="split-line"></div>
          <div class="primary-data">1,024</div>
          <div class="compare-data">
            <i class="fa fa-mobile" style="color: #1890FF"/> 手机: 512
            <i class="fa fa-desktop" style="color: #1890FF; margin-left: 0.5rem;"/> 电脑: 512</div>
        </div>

        <div class="card-item">
          <div class="card-title"><i class="fa fa-dashboard" style="color: #41c596"/> 系统状态</div>
          <div class="split-line"></div>
          <div class="primary-data">正常</div>
          <div class="compare-data"><i class="fa fa-warning" style="color: #FACC14"/> 无警告</div>
        </div>

        <div class="card-item">
          <div class="card-title"><i class="fa fa-eye" style="color: #8543E0"/> 今日访问</div>
          <div class="split-line"></div>
          <div class="primary-data">9,798</div>
          <div class="compare-data"><i  class="fa fa-futbol-o" style="color: #e322c7"/> 总访问: 153,234</div>
        </div>
      </div>
        <div class="chart-container" id="chart1">图表</div>
        <div class="second-chart-container">
          <div class="chart-item" id="chart2">
            图表2
          </div>
          <div class="chart-item" id="chart3" style="margin-left: 1rem">
            图表3
          </div>
        </div>
    </div>
</template>

<script lang="ts">
import * as echarts from 'echarts';
import { ref } from 'vue';
import { onMounted } from 'vue';
export default {
  setup() {
    onMounted(() => {
      let echart1 = echarts.init(document.getElementById("chart1"));
      const option = {
        title: {
          text: '周统计'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {},
        toolbox: {
          show: true,
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            dataView: { readOnly: false },
            magicType: { type: ['line', 'bar'] },
            restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value} 人'
          }
        },
        series: [
          {
            name: '点击量',
            type: 'line',
            data: [10, 11, 13, 11, 12, 12, 9],
            markPoint: {
              data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
              ]
            },
            markLine: {
              data: [{ type: 'average', name: '平均值' }]
            }
          },
          {
            name: '用户量',
            type: 'line',
            data: [1, 0, 2, 5, 3, 2, 0],
            markPoint: {
              data: [{ name: '周最低', value: -2, xAxis: 1, yAxis: -1.5 }]
            },
            markLine: {
              data: [
                { type: 'average', name: 'Avg' },
                [
                  {
                    symbol: 'none',
                    x: '90%',
                    yAxis: 'max'
                  },
                  {
                    symbol: 'circle',
                    label: {
                      position: 'start',
                      formatter: '最高'
                    },
                    type: 'max',
                    name: '最高点'
                  }
                ]
              ]
            }
          }
        ]
      };
      echart1.setOption(option);

      let echart2 = echarts.init(document.getElementById("chart2"));

      const option2 = {
        title: {
          text: '五维分析'
        },
        legend: {
          data: ['用户行为', '用户行为2']
        },
        radar: {
          // shape: 'circle',
          indicator: [
            { name: '行为1', max: 6500 },
            { name: '行为2', max: 16000 },
            { name: '行为3', max: 30000 },
            { name: '行为4', max: 38000 },
            { name: '行为5', max: 52000 }
          ]
        },
        series: [
          {
            name: 'Budget vs spending',
            type: 'radar',
            data: [
              {
                value: [4200, 3000, 20000, 35000, 50000],
                name: '用户行为'
              },
              {
                value: [5000, 14000, 28000, 26000, 42000],
                name: '用户行为2'
              }
            ]
          }
        ]
      };

      echart2.setOption(option2);

      let echart3 = echarts.init(document.getElementById("chart3"));

      const option3 = {
        title: {
          text: '用户来源'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '来源',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '40',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { value: 1048, name: '搜索引擎' },
              { value: 735, name: '直接点击' },
              { value: 580, name: '邮箱地址' },
              { value: 484, name: '广告投放' },
              { value: 300, name: '社交媒体' }
            ]
          }
        ]
      };

      echart3.setOption(option3);

    });
    return {

    }
  }
}
</script>

<style lang="scss" scoped>
  .card-container {
    display: flex;
    width: 100%;
    height: 14rem;
    .card-item {
      margin-left: 1rem;
      background: #fff;
      width: 25%;
      height: 90%;
      .card-title {
        margin: {
          left: 1.71rem;
          top: 1.5rem;
        }
        color: rgba(0, 0, 0, 0.45);
      }
      .split-line {
        height: 0.1rem;
        background: #E8E8E8;
        margin: 1rem;
      }
      .primary-data {
        font-size: 2.14rem;
        color: rgba(0, 0, 0, 0.6);
        margin: {
          left: 1.71rem;
          top: 0.5rem;
        }
      }

      .compare-data {
        margin: {
          left: 1.71rem;
          top: 0.7rem;
        }
      }

    }
  }
  .chart-container {
    background: #fff;
    width: 100%;
    height: 28.86rem;
  }

  .second-chart-container {
    display: flex;
    height: 30rem;
    width: 100%;
    margin-top: 1rem;
    .chart-item {
      width: 100%;
      background: #fff;
    }
  }

  @media screen and (max-width: 900px) {
    .card-container {
      height: auto;
      flex-direction: column;
      .card-item {
        width: 100%;
        height: 13rem;

        margin-left: 0rem;
        margin-top: 1rem;
      }
    }

    #chart1 {
      margin-top: 1rem;
    }
  }
</style>
