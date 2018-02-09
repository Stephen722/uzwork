/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : uskill2

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2018-02-09 22:31:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sk_address_city`
-- ----------------------------
DROP TABLE IF EXISTS `sk_address_city`;
CREATE TABLE `sk_address_city` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(5) NOT NULL DEFAULT '',
  `provinceId` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=405 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_address_city
-- ----------------------------
INSERT INTO `sk_address_city` VALUES ('60', '北京', '10');
INSERT INTO `sk_address_city` VALUES ('61', '天津', '11');
INSERT INTO `sk_address_city` VALUES ('62', '石家庄', '12');
INSERT INTO `sk_address_city` VALUES ('63', '唐山', '12');
INSERT INTO `sk_address_city` VALUES ('64', '秦皇岛', '12');
INSERT INTO `sk_address_city` VALUES ('65', '邯郸', '12');
INSERT INTO `sk_address_city` VALUES ('66', '邢台', '12');
INSERT INTO `sk_address_city` VALUES ('67', '保定', '12');
INSERT INTO `sk_address_city` VALUES ('68', '张家口', '12');
INSERT INTO `sk_address_city` VALUES ('69', '承德', '12');
INSERT INTO `sk_address_city` VALUES ('70', '沧州', '12');
INSERT INTO `sk_address_city` VALUES ('71', '廊坊', '12');
INSERT INTO `sk_address_city` VALUES ('72', '衡水', '12');
INSERT INTO `sk_address_city` VALUES ('73', '太原', '13');
INSERT INTO `sk_address_city` VALUES ('74', '大同', '13');
INSERT INTO `sk_address_city` VALUES ('75', '阳泉', '13');
INSERT INTO `sk_address_city` VALUES ('76', '长治', '13');
INSERT INTO `sk_address_city` VALUES ('77', '晋城', '13');
INSERT INTO `sk_address_city` VALUES ('78', '朔州', '13');
INSERT INTO `sk_address_city` VALUES ('79', '晋中', '13');
INSERT INTO `sk_address_city` VALUES ('80', '运城', '13');
INSERT INTO `sk_address_city` VALUES ('81', '忻州', '13');
INSERT INTO `sk_address_city` VALUES ('82', '临汾', '13');
INSERT INTO `sk_address_city` VALUES ('83', '吕梁', '13');
INSERT INTO `sk_address_city` VALUES ('84', '呼和浩特', '14');
INSERT INTO `sk_address_city` VALUES ('85', '包头', '14');
INSERT INTO `sk_address_city` VALUES ('86', '乌海', '14');
INSERT INTO `sk_address_city` VALUES ('87', '赤峰', '14');
INSERT INTO `sk_address_city` VALUES ('88', '通辽', '14');
INSERT INTO `sk_address_city` VALUES ('89', '鄂尔多斯', '14');
INSERT INTO `sk_address_city` VALUES ('90', '呼伦贝尔', '14');
INSERT INTO `sk_address_city` VALUES ('91', '巴彦淖尔', '14');
INSERT INTO `sk_address_city` VALUES ('92', '乌兰察布', '14');
INSERT INTO `sk_address_city` VALUES ('93', '兴安盟', '14');
INSERT INTO `sk_address_city` VALUES ('94', '锡林郭勒', '14');
INSERT INTO `sk_address_city` VALUES ('95', '阿拉善', '14');
INSERT INTO `sk_address_city` VALUES ('96', '沈阳', '15');
INSERT INTO `sk_address_city` VALUES ('97', '大连', '15');
INSERT INTO `sk_address_city` VALUES ('98', '鞍山', '15');
INSERT INTO `sk_address_city` VALUES ('99', '抚顺', '15');
INSERT INTO `sk_address_city` VALUES ('100', '本溪', '15');
INSERT INTO `sk_address_city` VALUES ('101', '丹东', '15');
INSERT INTO `sk_address_city` VALUES ('102', '锦州', '15');
INSERT INTO `sk_address_city` VALUES ('103', '营口', '15');
INSERT INTO `sk_address_city` VALUES ('104', '阜新', '15');
INSERT INTO `sk_address_city` VALUES ('105', '辽阳', '15');
INSERT INTO `sk_address_city` VALUES ('106', '盘锦', '15');
INSERT INTO `sk_address_city` VALUES ('107', '铁岭', '15');
INSERT INTO `sk_address_city` VALUES ('108', '朝阳', '15');
INSERT INTO `sk_address_city` VALUES ('109', '葫芦岛', '15');
INSERT INTO `sk_address_city` VALUES ('110', '长春', '16');
INSERT INTO `sk_address_city` VALUES ('111', '吉林', '16');
INSERT INTO `sk_address_city` VALUES ('112', '四平', '16');
INSERT INTO `sk_address_city` VALUES ('113', '辽源', '16');
INSERT INTO `sk_address_city` VALUES ('114', '通化', '16');
INSERT INTO `sk_address_city` VALUES ('115', '白山', '16');
INSERT INTO `sk_address_city` VALUES ('116', '松原', '16');
INSERT INTO `sk_address_city` VALUES ('117', '白城', '16');
INSERT INTO `sk_address_city` VALUES ('118', '延边', '16');
INSERT INTO `sk_address_city` VALUES ('119', '哈尔滨', '17');
INSERT INTO `sk_address_city` VALUES ('120', '齐齐哈尔', '17');
INSERT INTO `sk_address_city` VALUES ('121', '鸡西', '17');
INSERT INTO `sk_address_city` VALUES ('122', '鹤岗', '17');
INSERT INTO `sk_address_city` VALUES ('123', '双鸭山', '17');
INSERT INTO `sk_address_city` VALUES ('124', '大庆', '17');
INSERT INTO `sk_address_city` VALUES ('125', '伊春', '17');
INSERT INTO `sk_address_city` VALUES ('126', '佳木斯', '17');
INSERT INTO `sk_address_city` VALUES ('127', '七台河', '17');
INSERT INTO `sk_address_city` VALUES ('128', '牡丹江', '17');
INSERT INTO `sk_address_city` VALUES ('129', '黑河', '17');
INSERT INTO `sk_address_city` VALUES ('130', '绥化', '17');
INSERT INTO `sk_address_city` VALUES ('131', '大兴安岭', '17');
INSERT INTO `sk_address_city` VALUES ('132', '上海', '18');
INSERT INTO `sk_address_city` VALUES ('133', '南京', '19');
INSERT INTO `sk_address_city` VALUES ('134', '无锡', '19');
INSERT INTO `sk_address_city` VALUES ('135', '徐州', '19');
INSERT INTO `sk_address_city` VALUES ('136', '常州', '19');
INSERT INTO `sk_address_city` VALUES ('137', '苏州', '19');
INSERT INTO `sk_address_city` VALUES ('138', '南通', '19');
INSERT INTO `sk_address_city` VALUES ('139', '连云港', '19');
INSERT INTO `sk_address_city` VALUES ('140', '淮安', '19');
INSERT INTO `sk_address_city` VALUES ('141', '盐城', '19');
INSERT INTO `sk_address_city` VALUES ('142', '扬州', '19');
INSERT INTO `sk_address_city` VALUES ('143', '镇江', '19');
INSERT INTO `sk_address_city` VALUES ('144', '泰州', '19');
INSERT INTO `sk_address_city` VALUES ('145', '宿迁', '19');
INSERT INTO `sk_address_city` VALUES ('146', '杭州', '20');
INSERT INTO `sk_address_city` VALUES ('147', '宁波', '20');
INSERT INTO `sk_address_city` VALUES ('148', '温州', '20');
INSERT INTO `sk_address_city` VALUES ('149', '嘉兴', '20');
INSERT INTO `sk_address_city` VALUES ('150', '湖州', '20');
INSERT INTO `sk_address_city` VALUES ('151', '绍兴', '20');
INSERT INTO `sk_address_city` VALUES ('152', '金华', '20');
INSERT INTO `sk_address_city` VALUES ('153', '衢州', '20');
INSERT INTO `sk_address_city` VALUES ('154', '舟山', '20');
INSERT INTO `sk_address_city` VALUES ('155', '台州', '20');
INSERT INTO `sk_address_city` VALUES ('156', '丽水', '20');
INSERT INTO `sk_address_city` VALUES ('157', '合肥', '21');
INSERT INTO `sk_address_city` VALUES ('158', '芜湖', '21');
INSERT INTO `sk_address_city` VALUES ('159', '蚌埠', '21');
INSERT INTO `sk_address_city` VALUES ('160', '淮南', '21');
INSERT INTO `sk_address_city` VALUES ('161', '马鞍山', '21');
INSERT INTO `sk_address_city` VALUES ('162', '淮北', '21');
INSERT INTO `sk_address_city` VALUES ('163', '铜陵', '21');
INSERT INTO `sk_address_city` VALUES ('164', '安庆', '21');
INSERT INTO `sk_address_city` VALUES ('165', '黄山', '21');
INSERT INTO `sk_address_city` VALUES ('166', '滁州', '21');
INSERT INTO `sk_address_city` VALUES ('167', '阜阳', '21');
INSERT INTO `sk_address_city` VALUES ('168', '宿州', '21');
INSERT INTO `sk_address_city` VALUES ('169', '巢湖', '21');
INSERT INTO `sk_address_city` VALUES ('170', '六安', '21');
INSERT INTO `sk_address_city` VALUES ('171', '亳州', '21');
INSERT INTO `sk_address_city` VALUES ('172', '池州', '21');
INSERT INTO `sk_address_city` VALUES ('173', '宣城', '21');
INSERT INTO `sk_address_city` VALUES ('174', '福州', '22');
INSERT INTO `sk_address_city` VALUES ('175', '厦门', '22');
INSERT INTO `sk_address_city` VALUES ('176', '莆田', '22');
INSERT INTO `sk_address_city` VALUES ('177', '三明', '22');
INSERT INTO `sk_address_city` VALUES ('178', '泉州', '22');
INSERT INTO `sk_address_city` VALUES ('179', '漳州', '22');
INSERT INTO `sk_address_city` VALUES ('180', '南平', '22');
INSERT INTO `sk_address_city` VALUES ('181', '龙岩', '22');
INSERT INTO `sk_address_city` VALUES ('182', '宁德', '22');
INSERT INTO `sk_address_city` VALUES ('183', '南昌', '23');
INSERT INTO `sk_address_city` VALUES ('184', '景德镇', '23');
INSERT INTO `sk_address_city` VALUES ('185', '萍乡', '23');
INSERT INTO `sk_address_city` VALUES ('186', '九江', '23');
INSERT INTO `sk_address_city` VALUES ('187', '新余', '23');
INSERT INTO `sk_address_city` VALUES ('188', '鹰潭', '23');
INSERT INTO `sk_address_city` VALUES ('189', '赣州', '23');
INSERT INTO `sk_address_city` VALUES ('190', '吉安', '23');
INSERT INTO `sk_address_city` VALUES ('191', '宜春', '23');
INSERT INTO `sk_address_city` VALUES ('192', '抚州', '23');
INSERT INTO `sk_address_city` VALUES ('193', '上饶', '23');
INSERT INTO `sk_address_city` VALUES ('194', '济南', '24');
INSERT INTO `sk_address_city` VALUES ('195', '青岛', '24');
INSERT INTO `sk_address_city` VALUES ('196', '淄博', '24');
INSERT INTO `sk_address_city` VALUES ('197', '枣庄', '24');
INSERT INTO `sk_address_city` VALUES ('198', '东营', '24');
INSERT INTO `sk_address_city` VALUES ('199', '烟台', '24');
INSERT INTO `sk_address_city` VALUES ('200', '潍坊', '24');
INSERT INTO `sk_address_city` VALUES ('201', '济宁', '24');
INSERT INTO `sk_address_city` VALUES ('202', '泰安', '24');
INSERT INTO `sk_address_city` VALUES ('203', '威海', '24');
INSERT INTO `sk_address_city` VALUES ('204', '日照', '24');
INSERT INTO `sk_address_city` VALUES ('205', '莱芜', '24');
INSERT INTO `sk_address_city` VALUES ('206', '临沂', '24');
INSERT INTO `sk_address_city` VALUES ('207', '德州', '24');
INSERT INTO `sk_address_city` VALUES ('208', '聊城', '24');
INSERT INTO `sk_address_city` VALUES ('209', '滨州', '24');
INSERT INTO `sk_address_city` VALUES ('210', '荷泽', '24');
INSERT INTO `sk_address_city` VALUES ('211', '郑州', '25');
INSERT INTO `sk_address_city` VALUES ('212', '开封', '25');
INSERT INTO `sk_address_city` VALUES ('213', '洛阳', '25');
INSERT INTO `sk_address_city` VALUES ('214', '平顶山', '25');
INSERT INTO `sk_address_city` VALUES ('215', '安阳', '25');
INSERT INTO `sk_address_city` VALUES ('216', '鹤壁', '25');
INSERT INTO `sk_address_city` VALUES ('217', '新乡', '25');
INSERT INTO `sk_address_city` VALUES ('218', '焦作', '25');
INSERT INTO `sk_address_city` VALUES ('219', '濮阳', '25');
INSERT INTO `sk_address_city` VALUES ('220', '许昌', '25');
INSERT INTO `sk_address_city` VALUES ('221', '漯河', '25');
INSERT INTO `sk_address_city` VALUES ('222', '三门峡', '25');
INSERT INTO `sk_address_city` VALUES ('223', '南阳', '25');
INSERT INTO `sk_address_city` VALUES ('224', '商丘', '25');
INSERT INTO `sk_address_city` VALUES ('225', '信阳', '25');
INSERT INTO `sk_address_city` VALUES ('226', '周口', '25');
INSERT INTO `sk_address_city` VALUES ('227', '驻马店', '25');
INSERT INTO `sk_address_city` VALUES ('228', '武汉', '26');
INSERT INTO `sk_address_city` VALUES ('229', '黄石', '26');
INSERT INTO `sk_address_city` VALUES ('230', '十堰', '26');
INSERT INTO `sk_address_city` VALUES ('231', '宜昌', '26');
INSERT INTO `sk_address_city` VALUES ('232', '襄樊', '26');
INSERT INTO `sk_address_city` VALUES ('233', '鄂州', '26');
INSERT INTO `sk_address_city` VALUES ('234', '荆门', '26');
INSERT INTO `sk_address_city` VALUES ('235', '孝感', '26');
INSERT INTO `sk_address_city` VALUES ('236', '荆州', '26');
INSERT INTO `sk_address_city` VALUES ('237', '黄冈', '26');
INSERT INTO `sk_address_city` VALUES ('238', '咸宁', '26');
INSERT INTO `sk_address_city` VALUES ('239', '随州', '26');
INSERT INTO `sk_address_city` VALUES ('240', '恩施', '26');
INSERT INTO `sk_address_city` VALUES ('241', '神农架', '26');
INSERT INTO `sk_address_city` VALUES ('242', '长沙', '27');
INSERT INTO `sk_address_city` VALUES ('243', '株洲', '27');
INSERT INTO `sk_address_city` VALUES ('244', '湘潭', '27');
INSERT INTO `sk_address_city` VALUES ('245', '衡阳', '27');
INSERT INTO `sk_address_city` VALUES ('246', '邵阳', '27');
INSERT INTO `sk_address_city` VALUES ('247', '岳阳', '27');
INSERT INTO `sk_address_city` VALUES ('248', '常德', '27');
INSERT INTO `sk_address_city` VALUES ('249', '张家界', '27');
INSERT INTO `sk_address_city` VALUES ('250', '益阳', '27');
INSERT INTO `sk_address_city` VALUES ('251', '郴州', '27');
INSERT INTO `sk_address_city` VALUES ('252', '永州', '27');
INSERT INTO `sk_address_city` VALUES ('253', '怀化', '27');
INSERT INTO `sk_address_city` VALUES ('254', '娄底', '27');
INSERT INTO `sk_address_city` VALUES ('255', '湘西', '27');
INSERT INTO `sk_address_city` VALUES ('256', '广州', '28');
INSERT INTO `sk_address_city` VALUES ('257', '韶关', '28');
INSERT INTO `sk_address_city` VALUES ('258', '深圳', '28');
INSERT INTO `sk_address_city` VALUES ('259', '珠海', '28');
INSERT INTO `sk_address_city` VALUES ('260', '汕头', '28');
INSERT INTO `sk_address_city` VALUES ('261', '佛山', '28');
INSERT INTO `sk_address_city` VALUES ('262', '江门', '28');
INSERT INTO `sk_address_city` VALUES ('263', '湛江', '28');
INSERT INTO `sk_address_city` VALUES ('264', '茂名', '28');
INSERT INTO `sk_address_city` VALUES ('265', '肇庆', '28');
INSERT INTO `sk_address_city` VALUES ('266', '惠州', '28');
INSERT INTO `sk_address_city` VALUES ('267', '梅州', '28');
INSERT INTO `sk_address_city` VALUES ('268', '汕尾', '28');
INSERT INTO `sk_address_city` VALUES ('269', '河源', '28');
INSERT INTO `sk_address_city` VALUES ('270', '阳江', '28');
INSERT INTO `sk_address_city` VALUES ('271', '清远', '28');
INSERT INTO `sk_address_city` VALUES ('272', '东莞', '28');
INSERT INTO `sk_address_city` VALUES ('273', '中山', '28');
INSERT INTO `sk_address_city` VALUES ('274', '潮州', '28');
INSERT INTO `sk_address_city` VALUES ('275', '揭阳', '28');
INSERT INTO `sk_address_city` VALUES ('276', '云浮', '28');
INSERT INTO `sk_address_city` VALUES ('277', '南宁', '29');
INSERT INTO `sk_address_city` VALUES ('278', '柳州', '29');
INSERT INTO `sk_address_city` VALUES ('279', '桂林', '29');
INSERT INTO `sk_address_city` VALUES ('280', '梧州', '29');
INSERT INTO `sk_address_city` VALUES ('281', '北海', '29');
INSERT INTO `sk_address_city` VALUES ('282', '防城港', '29');
INSERT INTO `sk_address_city` VALUES ('283', '钦州', '29');
INSERT INTO `sk_address_city` VALUES ('284', '贵港', '29');
INSERT INTO `sk_address_city` VALUES ('285', '玉林', '29');
INSERT INTO `sk_address_city` VALUES ('286', '百色', '29');
INSERT INTO `sk_address_city` VALUES ('287', '贺州', '29');
INSERT INTO `sk_address_city` VALUES ('288', '河池', '29');
INSERT INTO `sk_address_city` VALUES ('289', '来宾', '29');
INSERT INTO `sk_address_city` VALUES ('290', '崇左', '29');
INSERT INTO `sk_address_city` VALUES ('291', '海口', '30');
INSERT INTO `sk_address_city` VALUES ('292', '三亚', '30');
INSERT INTO `sk_address_city` VALUES ('293', '重庆', '31');
INSERT INTO `sk_address_city` VALUES ('294', '成都', '32');
INSERT INTO `sk_address_city` VALUES ('295', '自贡', '32');
INSERT INTO `sk_address_city` VALUES ('296', '攀枝花', '32');
INSERT INTO `sk_address_city` VALUES ('297', '泸州', '32');
INSERT INTO `sk_address_city` VALUES ('298', '德阳', '32');
INSERT INTO `sk_address_city` VALUES ('299', '绵阳', '32');
INSERT INTO `sk_address_city` VALUES ('300', '广元', '32');
INSERT INTO `sk_address_city` VALUES ('301', '遂宁', '32');
INSERT INTO `sk_address_city` VALUES ('302', '内江', '32');
INSERT INTO `sk_address_city` VALUES ('303', '乐山', '32');
INSERT INTO `sk_address_city` VALUES ('304', '南充', '32');
INSERT INTO `sk_address_city` VALUES ('305', '眉山', '32');
INSERT INTO `sk_address_city` VALUES ('306', '宜宾', '32');
INSERT INTO `sk_address_city` VALUES ('307', '广安', '32');
INSERT INTO `sk_address_city` VALUES ('308', '达州', '32');
INSERT INTO `sk_address_city` VALUES ('309', '雅安', '32');
INSERT INTO `sk_address_city` VALUES ('310', '巴中', '32');
INSERT INTO `sk_address_city` VALUES ('311', '资阳', '32');
INSERT INTO `sk_address_city` VALUES ('312', '阿坝', '32');
INSERT INTO `sk_address_city` VALUES ('313', '甘孜', '32');
INSERT INTO `sk_address_city` VALUES ('314', '凉山', '32');
INSERT INTO `sk_address_city` VALUES ('315', '贵阳', '33');
INSERT INTO `sk_address_city` VALUES ('316', '六盘水', '33');
INSERT INTO `sk_address_city` VALUES ('317', '遵义', '33');
INSERT INTO `sk_address_city` VALUES ('318', '安顺', '33');
INSERT INTO `sk_address_city` VALUES ('319', '铜仁', '33');
INSERT INTO `sk_address_city` VALUES ('320', '黔西南', '33');
INSERT INTO `sk_address_city` VALUES ('321', '毕节', '33');
INSERT INTO `sk_address_city` VALUES ('322', '黔东南', '33');
INSERT INTO `sk_address_city` VALUES ('323', '黔南', '33');
INSERT INTO `sk_address_city` VALUES ('324', '昆明', '34');
INSERT INTO `sk_address_city` VALUES ('325', '曲靖', '34');
INSERT INTO `sk_address_city` VALUES ('326', '玉溪', '34');
INSERT INTO `sk_address_city` VALUES ('327', '保山', '34');
INSERT INTO `sk_address_city` VALUES ('328', '昭通', '34');
INSERT INTO `sk_address_city` VALUES ('329', '丽江', '34');
INSERT INTO `sk_address_city` VALUES ('330', '思茅', '34');
INSERT INTO `sk_address_city` VALUES ('331', '临沧', '34');
INSERT INTO `sk_address_city` VALUES ('332', '楚雄', '34');
INSERT INTO `sk_address_city` VALUES ('333', '红河', '34');
INSERT INTO `sk_address_city` VALUES ('334', '文山', '34');
INSERT INTO `sk_address_city` VALUES ('335', '西双版纳', '34');
INSERT INTO `sk_address_city` VALUES ('336', '大理', '34');
INSERT INTO `sk_address_city` VALUES ('337', '德宏', '34');
INSERT INTO `sk_address_city` VALUES ('338', '怒江', '34');
INSERT INTO `sk_address_city` VALUES ('339', '迪庆', '34');
INSERT INTO `sk_address_city` VALUES ('340', '拉萨', '35');
INSERT INTO `sk_address_city` VALUES ('341', '昌都', '35');
INSERT INTO `sk_address_city` VALUES ('342', '山南', '35');
INSERT INTO `sk_address_city` VALUES ('343', '日喀则', '35');
INSERT INTO `sk_address_city` VALUES ('344', '那曲', '35');
INSERT INTO `sk_address_city` VALUES ('345', '阿里', '35');
INSERT INTO `sk_address_city` VALUES ('346', '林芝', '35');
INSERT INTO `sk_address_city` VALUES ('347', '西安', '36');
INSERT INTO `sk_address_city` VALUES ('348', '铜川', '36');
INSERT INTO `sk_address_city` VALUES ('349', '宝鸡', '36');
INSERT INTO `sk_address_city` VALUES ('350', '咸阳', '36');
INSERT INTO `sk_address_city` VALUES ('351', '渭南', '36');
INSERT INTO `sk_address_city` VALUES ('352', '延安', '36');
INSERT INTO `sk_address_city` VALUES ('353', '汉中', '36');
INSERT INTO `sk_address_city` VALUES ('354', '榆林', '36');
INSERT INTO `sk_address_city` VALUES ('355', '安康', '36');
INSERT INTO `sk_address_city` VALUES ('356', '商洛', '36');
INSERT INTO `sk_address_city` VALUES ('357', '兰州', '37');
INSERT INTO `sk_address_city` VALUES ('358', '嘉峪关', '37');
INSERT INTO `sk_address_city` VALUES ('359', '金昌', '37');
INSERT INTO `sk_address_city` VALUES ('360', '白银', '37');
INSERT INTO `sk_address_city` VALUES ('361', '天水', '37');
INSERT INTO `sk_address_city` VALUES ('362', '武威', '37');
INSERT INTO `sk_address_city` VALUES ('363', '张掖', '37');
INSERT INTO `sk_address_city` VALUES ('364', '平凉', '37');
INSERT INTO `sk_address_city` VALUES ('365', '酒泉', '37');
INSERT INTO `sk_address_city` VALUES ('366', '庆阳', '37');
INSERT INTO `sk_address_city` VALUES ('367', '定西', '37');
INSERT INTO `sk_address_city` VALUES ('368', '陇南', '37');
INSERT INTO `sk_address_city` VALUES ('369', '临夏', '37');
INSERT INTO `sk_address_city` VALUES ('370', '甘南', '37');
INSERT INTO `sk_address_city` VALUES ('371', '西宁', '38');
INSERT INTO `sk_address_city` VALUES ('372', '海东', '38');
INSERT INTO `sk_address_city` VALUES ('373', '海北', '38');
INSERT INTO `sk_address_city` VALUES ('374', '黄南', '38');
INSERT INTO `sk_address_city` VALUES ('375', '海南', '38');
INSERT INTO `sk_address_city` VALUES ('376', '果洛', '38');
INSERT INTO `sk_address_city` VALUES ('377', '玉树', '38');
INSERT INTO `sk_address_city` VALUES ('378', '海西', '38');
INSERT INTO `sk_address_city` VALUES ('379', '银川', '39');
INSERT INTO `sk_address_city` VALUES ('380', '石嘴山', '39');
INSERT INTO `sk_address_city` VALUES ('381', '吴忠', '39');
INSERT INTO `sk_address_city` VALUES ('382', '固原', '39');
INSERT INTO `sk_address_city` VALUES ('383', '中卫', '39');
INSERT INTO `sk_address_city` VALUES ('384', '乌鲁木齐', '40');
INSERT INTO `sk_address_city` VALUES ('385', '克拉玛依', '40');
INSERT INTO `sk_address_city` VALUES ('386', '吐鲁番', '40');
INSERT INTO `sk_address_city` VALUES ('387', '哈密', '40');
INSERT INTO `sk_address_city` VALUES ('388', '昌吉', '40');
INSERT INTO `sk_address_city` VALUES ('389', '博尔塔拉', '40');
INSERT INTO `sk_address_city` VALUES ('390', '巴音郭楞', '40');
INSERT INTO `sk_address_city` VALUES ('391', '阿克苏', '40');
INSERT INTO `sk_address_city` VALUES ('392', '克孜勒苏', '40');
INSERT INTO `sk_address_city` VALUES ('393', '喀什', '40');
INSERT INTO `sk_address_city` VALUES ('394', '和田', '40');
INSERT INTO `sk_address_city` VALUES ('395', '伊犁', '40');
INSERT INTO `sk_address_city` VALUES ('396', '塔城', '40');
INSERT INTO `sk_address_city` VALUES ('397', '阿勒泰', '40');
INSERT INTO `sk_address_city` VALUES ('398', '石河子', '40');
INSERT INTO `sk_address_city` VALUES ('399', '阿拉尔', '40');
INSERT INTO `sk_address_city` VALUES ('400', '图木舒克', '40');
INSERT INTO `sk_address_city` VALUES ('401', '五家渠', '40');
INSERT INTO `sk_address_city` VALUES ('402', '香港', '41');
INSERT INTO `sk_address_city` VALUES ('403', '澳门', '42');
INSERT INTO `sk_address_city` VALUES ('404', '台湾', '43');

-- ----------------------------
-- Table structure for `sk_address_province`
-- ----------------------------
DROP TABLE IF EXISTS `sk_address_province`;
CREATE TABLE `sk_address_province` (
  `id` smallint(5) unsigned NOT NULL DEFAULT '0',
  `name` varchar(4) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_address_province
-- ----------------------------
INSERT INTO `sk_address_province` VALUES ('10', '北京');
INSERT INTO `sk_address_province` VALUES ('11', '天津');
INSERT INTO `sk_address_province` VALUES ('12', '河北');
INSERT INTO `sk_address_province` VALUES ('13', '山西');
INSERT INTO `sk_address_province` VALUES ('14', '内蒙古');
INSERT INTO `sk_address_province` VALUES ('15', '辽宁');
INSERT INTO `sk_address_province` VALUES ('16', '吉林');
INSERT INTO `sk_address_province` VALUES ('17', '黑龙江');
INSERT INTO `sk_address_province` VALUES ('18', '上海');
INSERT INTO `sk_address_province` VALUES ('19', '江苏');
INSERT INTO `sk_address_province` VALUES ('20', '浙江');
INSERT INTO `sk_address_province` VALUES ('21', '安徽');
INSERT INTO `sk_address_province` VALUES ('22', '福建');
INSERT INTO `sk_address_province` VALUES ('23', '江西');
INSERT INTO `sk_address_province` VALUES ('24', '山东');
INSERT INTO `sk_address_province` VALUES ('25', '河南');
INSERT INTO `sk_address_province` VALUES ('26', '湖北');
INSERT INTO `sk_address_province` VALUES ('27', '湖南');
INSERT INTO `sk_address_province` VALUES ('28', '广东');
INSERT INTO `sk_address_province` VALUES ('29', '广西');
INSERT INTO `sk_address_province` VALUES ('30', '海南');
INSERT INTO `sk_address_province` VALUES ('31', '重庆');
INSERT INTO `sk_address_province` VALUES ('32', '四川');
INSERT INTO `sk_address_province` VALUES ('33', '贵州');
INSERT INTO `sk_address_province` VALUES ('34', '云南');
INSERT INTO `sk_address_province` VALUES ('35', '西藏');
INSERT INTO `sk_address_province` VALUES ('36', '陕西');
INSERT INTO `sk_address_province` VALUES ('37', '甘肃');
INSERT INTO `sk_address_province` VALUES ('38', '青海');
INSERT INTO `sk_address_province` VALUES ('39', '宁夏');
INSERT INTO `sk_address_province` VALUES ('40', '新疆');
INSERT INTO `sk_address_province` VALUES ('41', '香港');
INSERT INTO `sk_address_province` VALUES ('42', '澳门');
INSERT INTO `sk_address_province` VALUES ('43', '台湾');

-- ----------------------------
-- Table structure for `sk_app_start`
-- ----------------------------
DROP TABLE IF EXISTS `sk_app_start`;
CREATE TABLE `sk_app_start` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `deviceId` varchar(40) NOT NULL DEFAULT '',
  `userId` int(10) NOT NULL DEFAULT '0' COMMENT 'it may be 0',
  `startIp` varchar(50) NOT NULL DEFAULT '',
  `startedTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  `deviceType` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0: Android 1: iOS 2: Windows 3:Others',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_app_start
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_app_version`
-- ----------------------------
DROP TABLE IF EXISTS `sk_app_version`;
CREATE TABLE `sk_app_version` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `versionId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'APP Version',
  `versionNumber` varchar(10) NOT NULL DEFAULT '' COMMENT 'Version Number',
  `forceUpdate` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT 'force to update',
  `app` varchar(10) NOT NULL DEFAULT '',
  `updateURL` varchar(100) NOT NULL DEFAULT '' COMMENT 'Update URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_app_version
-- ----------------------------
INSERT INTO `sk_app_version` VALUES ('1', '100', '1.0.0', '0', 'IOS', '');
INSERT INTO `sk_app_version` VALUES ('2', '100', '1.0.0', '0', 'ANDROID', '');

-- ----------------------------
-- Table structure for `sk_configuration`
-- ----------------------------
DROP TABLE IF EXISTS `sk_configuration`;
CREATE TABLE `sk_configuration` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `value` varchar(60) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `ad_config_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_configuration
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_favorite`
-- ----------------------------
DROP TABLE IF EXISTS `sk_favorite`;
CREATE TABLE `sk_favorite` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL DEFAULT '0',
  `skillId` int(10) unsigned NOT NULL DEFAULT '0',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `ind_fav_uid` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_favorite
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_feedback`
-- ----------------------------
DROP TABLE IF EXISTS `sk_feedback`;
CREATE TABLE `sk_feedback` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '反馈ID',
  `userId` int(10) unsigned NOT NULL DEFAULT '0',
  `content` varchar(400) NOT NULL DEFAULT '',
  `contact` varchar(20) NOT NULL DEFAULT '',
  `createdIp` varchar(30) NOT NULL DEFAULT '',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_skill`
-- ----------------------------
DROP TABLE IF EXISTS `sk_skill`;
CREATE TABLE `sk_skill` (
  `skillId` int(10) unsigned NOT NULL DEFAULT '0',
  `userId` int(10) unsigned NOT NULL DEFAULT '0',
  `content` varchar(500) NOT NULL DEFAULT '',
  `categoryId` smallint(5) unsigned NOT NULL DEFAULT '0',
  `imageB` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0: No 1: Yes',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`skillId`),
  KEY `ind_sk_uid` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_skill
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_skill_comment`
-- ----------------------------
DROP TABLE IF EXISTS `sk_skill_comment`;
CREATE TABLE `sk_skill_comment` (
  `id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `skillId` int(10) unsigned NOT NULL DEFAULT '0',
  `postUserId` int(10) unsigned NOT NULL DEFAULT '0',
  `targetUserId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'the target user id, 0 means for skill',
  `content` varchar(500) NOT NULL DEFAULT '',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `ind_sk_com_sid` (`skillId`) USING BTREE,
  KEY `ind_sk_com_puid` (`postUserId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_skill_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_skill_file`
-- ----------------------------
DROP TABLE IF EXISTS `sk_skill_file`;
CREATE TABLE `sk_skill_file` (
  `id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `skillId` int(10) unsigned NOT NULL DEFAULT '0',
  `path` varchar(20) NOT NULL DEFAULT '',
  `name` varchar(20) NOT NULL DEFAULT '',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `ind_sk_file_skid` (`skillId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_skill_file
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_skill_praise`
-- ----------------------------
DROP TABLE IF EXISTS `sk_skill_praise`;
CREATE TABLE `sk_skill_praise` (
  `id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `skillId` int(10) unsigned NOT NULL DEFAULT '0',
  `postUserId` int(10) unsigned NOT NULL DEFAULT '0',
  `targetUserId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'the target user id, 0 means for skill',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `ind_sk_ps_skid` (`skillId`) USING BTREE,
  KEY `ind_sk_ps_puid` (`postUserId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_skill_praise
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_topic`
-- ----------------------------
DROP TABLE IF EXISTS `sk_topic`;
CREATE TABLE `sk_topic` (
  `topicId` bigint(10) unsigned NOT NULL,
  `userId` int(10) unsigned NOT NULL DEFAULT '0',
  `content` varchar(2000) NOT NULL DEFAULT '',
  `imageB` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0: No 1: Yes',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`topicId`),
  KEY `ind_tp_uid` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_topic
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_topic_comment`
-- ----------------------------
DROP TABLE IF EXISTS `sk_topic_comment`;
CREATE TABLE `sk_topic_comment` (
  `id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `topicId` bigint(10) unsigned NOT NULL DEFAULT '0',
  `postUserId` int(10) unsigned NOT NULL DEFAULT '0',
  `targetUserId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'the target user id, 0 means for topic',
  `content` varchar(500) NOT NULL DEFAULT '',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `ind_tp_com_tpid` (`topicId`) USING BTREE,
  KEY `ind_tp_com_puid` (`postUserId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_topic_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_topic_picture`
-- ----------------------------
DROP TABLE IF EXISTS `sk_topic_picture`;
CREATE TABLE `sk_topic_picture` (
  `id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `topicId` bigint(10) unsigned NOT NULL DEFAULT '0',
  `path` varchar(20) NOT NULL DEFAULT '',
  `name` varchar(20) NOT NULL DEFAULT '',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `ind_tp_pic_tpid` (`topicId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_topic_picture
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_topic_praise`
-- ----------------------------
DROP TABLE IF EXISTS `sk_topic_praise`;
CREATE TABLE `sk_topic_praise` (
  `id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `topicId` bigint(10) unsigned NOT NULL DEFAULT '0',
  `postUserId` int(10) unsigned NOT NULL DEFAULT '0',
  `targetUserId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'the target user id, 0 means for topic',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `ind_tp_ps_tpid` (`topicId`) USING BTREE,
  KEY `ind_tp_ps_puid` (`postUserId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_topic_praise
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_user`
-- ----------------------------
DROP TABLE IF EXISTS `sk_user`;
CREATE TABLE `sk_user` (
  `userId` int(10) unsigned NOT NULL,
  `phone` char(11) NOT NULL DEFAULT '',
  `nickname` varchar(30) NOT NULL DEFAULT '',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT 'unique user name',
  `password` varchar(40) NOT NULL DEFAULT '',
  `sex` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0:empty 1:male 2:female',
  `picture` varchar(20) NOT NULL DEFAULT '' COMMENT 'profile picture path and name',
  `brief` varchar(60) NOT NULL DEFAULT '' COMMENT 'user brief in short',
  `city` varchar(10) NOT NULL DEFAULT '',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  `createdIp` varchar(50) NOT NULL DEFAULT '',
  `logins` int(10) unsigned NOT NULL DEFAULT '0',
  `loginTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  `loginIp` varchar(50) NOT NULL DEFAULT '',
  `activeB` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '0: inactive 1:active',
  `friends` mediumint(5) unsigned NOT NULL DEFAULT '0' COMMENT 'the number of friend',
  `messages` mediumint(5) unsigned NOT NULL DEFAULT '0' COMMENT 'the number of unread message',
  `favorites` mediumint(5) unsigned NOT NULL DEFAULT '0' COMMENT 'the number of favorite skill',
  PRIMARY KEY (`userId`),
  KEY `ind_user_phone` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_user
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_user_friend`
-- ----------------------------
DROP TABLE IF EXISTS `sk_user_friend`;
CREATE TABLE `sk_user_friend` (
  `id` bigint(10) unsigned NOT NULL DEFAULT '0',
  `fromUserId` int(10) unsigned NOT NULL DEFAULT '0',
  `toUserId` int(10) unsigned NOT NULL DEFAULT '0',
  `both` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0: not accepted 1: both 2: only ''from'' has the friend of ''to'' 3: only ''to'' has the friend of ''from'' 4: they have been deleted each other',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT 'the time of application',
  `acceptedTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT 'the time of first acceptance',
  `changedTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `ind_friend_fuid` (`fromUserId`) USING BTREE,
  KEY `ind_friend_tuid` (`toUserId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_user_friend
-- ----------------------------

-- ----------------------------
-- Table structure for `sk_user_picture`
-- ----------------------------
DROP TABLE IF EXISTS `sk_user_picture`;
CREATE TABLE `sk_user_picture` (
  `id` int(10) unsigned NOT NULL,
  `userId` int(10) unsigned NOT NULL DEFAULT '0',
  `path` varchar(20) NOT NULL DEFAULT '',
  `name` varchar(20) NOT NULL DEFAULT '',
  `createdTime` datetime NOT NULL DEFAULT '2000-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `ind_user_pic_uid` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_user_picture
-- ----------------------------
