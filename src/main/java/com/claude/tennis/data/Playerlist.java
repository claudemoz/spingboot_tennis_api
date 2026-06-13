package com.claude.tennis.data;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import com.claude.tennis.dto.PlayerDto;
import com.claude.tennis.dto.RankDto;

public class Playerlist {

        public static PlayerDto RAFAEL_NADAL = new PlayerDto(
                        "Rafael",
                        "Nadal",
                        LocalDate.of(1986, Month.JUNE, 3),
                        new RankDto(1, 5000));

        public static PlayerDto NOVAK_DJOKOVIC = new PlayerDto(
                        "Novak",
                        "Djokovic",
                        LocalDate.of(1987, Month.MAY, 22),
                        new RankDto(2, 4000));

        public static PlayerDto ROGER_FEDERER = new PlayerDto(
                        "Roger",
                        "Federer",
                        LocalDate.of(1981, Month.AUGUST, 8),
                        new RankDto(3, 3000));

        public static PlayerDto ANDY_MURRAY = new PlayerDto(
                        "Andy",
                        "Murray",
                        LocalDate.of(1987, Month.MAY, 15),
                        new RankDto(4, 2000));

        public static List<PlayerDto> ALL = Arrays.asList(RAFAEL_NADAL, NOVAK_DJOKOVIC, ROGER_FEDERER, ANDY_MURRAY);
}
