CREATE TABLE `dictionary`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `word`        varchar(50)  NOT NULL,
    `description` varchar(255) NOT NULL,
    `img_url`     varchar(255) NULL
);

CREATE TABLE `car`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL
);

CREATE TABLE `category`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL
);

CREATE TABLE `option`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `name`        varchar(100) NOT NULL,
    `price`       int          NOT NULL,
    `feedback`    varchar(255) NULL,
    `category_id` bigint       NOT NULL,
    `parent_id`   bigint       NOT NULL
);

CREATE TABLE `sell`
(
    `id`                bigint    NOT NULL AUTO_INCREMENT,
    `trim_id`           bigint    NOT NULL,
    `engine_id`         bigint    NOT NULL,
    `body_type_id`      bigint    NOT NULL,
    `driving_system_id` bigint    NOT NULL,
    `exterior_color_id` bigint    NOT NULL,
    `interior_color_id` bigint    NOT NULL,
    `wheel_id`          bigint    NOT NULL,
    `age`               tinyint   NOT NULL,
    `gender`            tinyint   NOT NULL,
    `create_date`       timestamp NOT NULL
);

CREATE TABLE `sell_selective_options`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `sell_id`   bigint NOT NULL,
    `option_id` bigint NOT NULL
);

CREATE TABLE `sell_h_genuine_accessories`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `option_id` bigint NOT NULL,
    `sell_id`   bigint NOT NULL
);

CREATE TABLE `keyword`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL
);

CREATE TABLE `option_keyword`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `option_id`  bigint NOT NULL,
    `keyword_id` bigint NOT NULL
);

CREATE TABLE `estimate`
(
    `id`                bigint    NOT NULL AUTO_INCREMENT,
    `trim_id`           bigint    NOT NULL,
    `engine_id`         bigint    NOT NULL,
    `body_type_id`      bigint    NOT NULL,
    `driving_system_id` bigint    NOT NULL,
    `exterior_color_id` bigint    NOT NULL,
    `interior_color_id` bigint    NOT NULL,
    `wheel_id`          bigint    NOT NULL,
    `keyword1_id`       bigint    NOT NULL,
    `keyword2_id`       bigint    NOT NULL,
    `keyword3_id`       bigint    NOT NULL,
    `age`               tinyint   NOT NULL,
    `gender`            tinyint   NOT NULL,
    `create_date`       timestamp NOT NULL
);

CREATE TABLE `estimate_h_genuine_accessories`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `estimate_id` bigint NOT NULL,
    `option_id`   bigint NOT NULL
);

CREATE TABLE `estimate_selective_options`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `estimate_id` bigint NOT NULL,
    `option_id`   bigint NOT NULL
);

CREATE TABLE `option_image`
(
    `id`        bigint       NOT NULL AUTO_INCREMENT,
    `img_url`   varchar(255) NOT NULL,
    `img_type`  tinyint      NOT NULL,
    `option_id` bigint       NOT NULL
);

CREATE TABLE `detail`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `name`        varchar(100) NOT NULL,
    `description` varchar(255) NOT NULL,
    `img_url`     varchar(255) NULL,
    `option_id`   bigint       NOT NULL
);

CREATE TABLE `trim`
(
    `id`      bigint       NOT NULL AUTO_INCREMENT,
    `name`    varchar(50)  NOT NULL,
    `img_url` varchar(255) NOT NULL,
    `car_id`  bigint       NOT NULL
);

CREATE TABLE `trim_option`
(
    `id`         bigint  NOT NULL AUTO_INCREMENT,
    `type`       tinyint NOT NULL,
    `trim_id`    bigint  NOT NULL,
    `options_id` bigint  NOT NULL
);

CREATE TABLE `option_relation`
(
    `id`       VARCHAR(255) NOT NULL AUTO_INCREMENT
        `parent_id` bigint NOT NULL,
    `child_id` bigint       NOT NULL
);

ALTER TABLE `dictionary`
    ADD CONSTRAINT `PK_DICTIONARY` PRIMARY KEY (
                                                `id`
        );

ALTER TABLE `car`
    ADD CONSTRAINT `PK_CAR` PRIMARY KEY (
                                         `id`
        );

ALTER TABLE `category`
    ADD CONSTRAINT `PK_CATEGORY` PRIMARY KEY (
                                              `id`
        );

ALTER TABLE `option`
    ADD CONSTRAINT `PK_OPTION` PRIMARY KEY (
                                            `id`
        );

ALTER TABLE `sell`
    ADD CONSTRAINT `PK_SELL` PRIMARY KEY (
                                          `id`
        );

ALTER TABLE `sell_selective_options`
    ADD CONSTRAINT `PK_SELL_SELECTIVE_OPTIONS` PRIMARY KEY (
                                                            `id`
        );

ALTER TABLE `sell_h_genuine_accessories`
    ADD CONSTRAINT `PK_SELL_H_GENUINE_ACCESSORIES` PRIMARY KEY (
                                                                `id`
        );

ALTER TABLE `keyword`
    ADD CONSTRAINT `PK_KEYWORD` PRIMARY KEY (
                                             `id`
        );

ALTER TABLE `option_keyword`
    ADD CONSTRAINT `PK_OPTION_KEYWORD` PRIMARY KEY (
                                                    `id`
        );

ALTER TABLE `estimate`
    ADD CONSTRAINT `PK_ESTIMATE` PRIMARY KEY (
                                              `id`
        );

ALTER TABLE `estimate_h_genuine_accessories`
    ADD CONSTRAINT `PK_ESTIMATE_H_GENUINE_ACCESSORIES` PRIMARY KEY (
                                                                    `id`
        );

ALTER TABLE `estimate_selective_options`
    ADD CONSTRAINT `PK_ESTIMATE_SELECTIVE_OPTIONS` PRIMARY KEY (
                                                                `id`
        );

ALTER TABLE `option_image`
    ADD CONSTRAINT `PK_OPTION_IMAGE` PRIMARY KEY (
                                                  `id`
        );

ALTER TABLE `detail`
    ADD CONSTRAINT `PK_DETAIL` PRIMARY KEY (
                                            `id`
        );

ALTER TABLE `trim`
    ADD CONSTRAINT `PK_TRIM` PRIMARY KEY (
                                          `id`
        );

ALTER TABLE `trim_option`
    ADD CONSTRAINT `PK_TRIM_OPTION` PRIMARY KEY (
                                                 `id`
        );

ALTER TABLE `option_relation`
    ADD CONSTRAINT `PK_OPTION_RELATION` PRIMARY KEY (
                                                     `id`
        );

