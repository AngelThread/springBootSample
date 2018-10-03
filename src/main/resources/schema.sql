CREATE TABLE `customer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `active` tinyint(1) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
);
INSERT INTO `customer` VALUES (1,'Big News Media Corp',1),(2,'Online Mega Store',1),(3,'Nachoroo Delivery',0),(4,'Euro Telecom Group',1);


CREATE TABLE `ua_blacklist` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ua` varchar(255) NOT NULL,
  PRIMARY KEY (`ua`)
);
 CREATE INDEX ua_idx ON ua_blacklist(ua);


INSERT INTO `ua_blacklist` VALUES (1,'A6-Indexer'),(2,'Googlebot-News'),(3,'Googlebot');


CREATE TABLE `ip_blacklist` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(11) unsigned NOT NULL,
  PRIMARY KEY (`ip`)
);
 CREATE INDEX ip_idx ON ip_blacklist(ip);


INSERT INTO `ip_blacklist` VALUES (1,'0'),(2,'2130706433'),(3,'4294967295');


CREATE TABLE `hourly_stats` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) unsigned NOT NULL,
  `time` timestamp NOT NULL,
  `request_count` bigint(20) unsigned NOT NULL DEFAULT '0',
  `invalid_count` bigint(20) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_customer_time` (`customer_id`,`time`),
   CONSTRAINT `hourly_stats_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
);

    CREATE INDEX customer_idx ON hourly_stats(customer_id);
    CREATE INDEX customer_timex ON hourly_stats(`time`);