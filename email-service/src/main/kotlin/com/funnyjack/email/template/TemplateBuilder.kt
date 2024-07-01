package com.funnyjack.email.template

import com.funnyjack.persistent.entity.JoinApplication
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日")

fun generateJoinApplicationTemplate(joinApplication: JoinApplication): Pair<String, String> {
    val emailSubject = "${joinApplication.referrerNumber}推${joinApplication.ownNumber}加${joinApplication.amount}"
    val content = """
    诚信共赢社区管理处：
        我姓${joinApplication.surname}，${joinApplication.gender}性，昵称${joinApplication.nickName}；居住地${joinApplication.address}。现申请加入诚信共赢社区，特声明如下：
        1.认同MMM思想理念，遵守诚信共赢社区相关规定。
        2.拥护诚信共赢社区指导方针：“无偿援助，互惠和仁爱”。
        3.明白“参与诚信共赢社区，本身就是愿意提供无偿援助的明确表示”，认同社区共识“参与社区等于自愿捐助”。
        4.承诺闲钱参与，不动用关键资金，绝不借贷参与。
        5.承诺不利用诚信共赢平台从事任何政治、宗教、黄赌毒及其他犯罪活动；不攻击、诽谤任何国家、政府、政党、组织、企业或个人。
        6.阅读了警告，完全了解所有风险，决定参与诚信共赢社区。本人心智健全，具有完全民事行为能力，能对自己的行为完全负责。
        申请人：${joinApplication.ownNumber}
        推荐人：${joinApplication.referrerNumber}
                                                                                       ${
        LocalDate.now().format(formatter)
    }
    """.trimIndent()
    return emailSubject to content
}
