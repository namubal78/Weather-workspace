# Weather-workspace
> 오늘의 날씨 조회 Rest API

[![NPM Version][npm-image]][npm-url]
[![Build Status][travis-image]][travis-url]
[![Downloads Stats][npm-downloads]][npm-url]

오늘 날씨에 대한 간단한 정보를 조회할 수 있는 Rest API입니다.

![](../header.png)

## 구현 기능
> (이하 기능들은 모두 소재 위, 경도 기준의 기상청_단기예보 데이터를 조회한 것입니다)

* 현재 시각, 예보 시각 조회
* 현재 하늘 상태, 기온 조회 
* 현재 최고 기온, 최저 기온 조회
* 기타 정보들(강수확률, 습도, 파고, 풍속) 조회

* 단기 날씨 예보 조회 - 예보 시각 기준으로 3시간 후, 6시간 후, 9시간 후의 기온 조회

## 개발 환경

```sh
Language : Java 8, CSS3, HTML5, JavaScript
DBMS: Oracle 11g EE
Library : jQuery 3.6.1, JSON
Tool :  STS(3.8.2), Oracle SQL Developer(21.2.1), Visual Studio Code(1.71.2)
Framework: Spring, MyBatis
WAS: Apache Tomcat (8.5.82)						
```

## 업데이트 내역

* 1.0.0
    * github readme upload

## 정보

한영섭 – [@티스토리 블로그](https://namubal78.tistory.com/) – namubal78@gmail.com

라이센스를 준수하며 ``LICENSE``에서 자세한 정보를 확인할 수 있습니다.

[LICENSE](https://github.com/namubal78/Weather-workspace/blob/main/LICENSE)

<!-- Markdown link & img dfn's -->
[npm-image]: https://img.shields.io/npm/v/datadog-metrics.svg?style=flat-square
[npm-url]: https://npmjs.org/package/datadog-metrics
[npm-downloads]: https://img.shields.io/npm/dm/datadog-metrics.svg?style=flat-square
[travis-image]: https://img.shields.io/travis/dbader/node-datadog-metrics/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/dbader/node-datadog-metrics
[wiki]: https://github.com/yourname/yourproject/wiki
