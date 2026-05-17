package com.claude.tennis;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import com.claude.tennis.entities.PlayerEntity;
import com.claude.tennis.entities.Rank;

public class Playerlist {

        public static PlayerEntity RAFAEL_NADAL = new PlayerEntity(
                        "Rafael",
                        "Nadal",
                        LocalDate.of(1986, Month.JUNE, 3),
                        new Rank(1, 5000));

        public static PlayerEntity NOVAK_DJOKOVIC = new PlayerEntity(
                        "Novak",
                        "Djokovic",
                        LocalDate.of(1987, Month.MAY, 22),
                        new Rank(2, 4000));

        public static PlayerEntity ROGER_FEDERER = new PlayerEntity(
                        "Roger",
                        "Federer",
                        LocalDate.of(1981, Month.AUGUST, 8),
                        new Rank(3, 3000));

        public static PlayerEntity ANDY_MURRAY = new PlayerEntity(
                        "Andy",
                        "Murray",
                        LocalDate.of(1987, Month.MAY, 15),
                        new Rank(4, 2000));

        public static List<PlayerEntity> ALL = Arrays.asList(ANDY_MURRAY, NOVAK_DJOKOVIC, RAFAEL_NADAL, ROGER_FEDERER);
}
