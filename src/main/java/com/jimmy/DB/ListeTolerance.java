package com.jimmy.DB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.jimmy.classes.ClasseDeTolerance;
import com.jimmy.classes.Ecart;
import com.jimmy.classes.Intervalle;
import com.jimmy.classes.Tolerance;
import com.jimmy.enumerations.TypeClasseDeTolerance;
import com.jimmy.util.Util;

public abstract class ListeTolerance {

	private static List<Tolerance> listeTolerance = new ArrayList<Tolerance>();
	private static boolean dbAReinitialiser = false;
	private static Connection connexion;

	static {

		// 1°) On établit la connexion
		ouvrirConnexion();

		// 2°) On recherche la propriété "dbAReinitialiser", si :
		// - true --> on (ré-)initialise toute la DB
		// - false --> on accède à la DB via les dao

		dbAReinitialiser = Boolean.valueOf(Util.recherchePropriete("dbAReinitialiser"));
		System.out.println("Status de la property 'dbAReinitialiser' = " + dbAReinitialiser);

		// 3°) On charge toutes les Tolerance

		List<String> listeString;

		if (dbAReinitialiser) {

			// 1°) On efface les contenus de toutes les tables

			int nbRecordEfface;

			IntervalleDaoImpl intervalleDaoImpl = new IntervalleDaoImpl();
			nbRecordEfface = intervalleDaoImpl.deleteAll(connexion);
			System.out.println("Nb records effacés dans table intervalle = " + nbRecordEfface);

			EcartDaoImpl ecartDaoImpl = new EcartDaoImpl();
			nbRecordEfface = ecartDaoImpl.deleteAll(connexion);
			System.out.println("Nb records effacés dans table ecart = " + nbRecordEfface);

			ClasseDeToleranceDaoImpl classeDeToleranceDaoImpl = new ClasseDeToleranceDaoImpl();
			nbRecordEfface = classeDeToleranceDaoImpl.deleteAll(connexion);
			System.out.println("Nb records effacés dans table classe_de_tolerance = " + nbRecordEfface);

			ToleranceDaoImpl toleranceDaoImpl = new ToleranceDaoImpl();
			nbRecordEfface = toleranceDaoImpl.deleteAll(connexion);
			System.out.println("Nb records effacés dans table tolerance = " + nbRecordEfface);

			// 2°) On recharge toutes les données

			listeString = Util.decoupageChaine(tolerancesAlesages1());
			listeTolerance.addAll(constructionListeTolerance(listeString));

			listeString = Util.decoupageChaine(tolerancesAlesages2());
			listeTolerance.addAll(constructionListeTolerance(listeString));

			listeString = Util.decoupageChaine(tolerancesArbres1());
			listeTolerance.addAll(constructionListeTolerance(listeString));

			listeString = Util.decoupageChaine(tolerancesArbres2());
			listeTolerance.addAll(constructionListeTolerance(listeString));

		} else {
			ToleranceDaoImpl toleranceDaoImpl = new ToleranceDaoImpl();
			listeTolerance = toleranceDaoImpl.getAll(connexion);
		}

		// On ferme la connexion

		fermerConnexion();

	}

	private static void ouvrirConnexion() {
		ConnexionDBMySql connexionDBMySql = new ConnexionDBMySql();
		connexion = connexionDBMySql.getConnexion();
	}

	private static void fermerConnexion() {
		ConnexionDBMySql connexionDBMySql = new ConnexionDBMySql();
		connexionDBMySql.closeConnexion(connexion);
	}

	private static List<Tolerance> getListeTolerance() {

		return listeTolerance;

	}

	private static List<ClasseDeTolerance> getListeClasseDeTolerance() {
		ClasseDeToleranceDaoImpl classeDeToleranceDaoImpl = new ClasseDeToleranceDaoImpl();

		return classeDeToleranceDaoImpl.getAll(connexion);

	}

	public static List<ClasseDeTolerance> getListeClasseDeTolerance(TypeClasseDeTolerance typeClasseDeTolerance) {
		ouvrirConnexion();
		ClasseDeToleranceDaoImpl classeDeToleranceDaoImpl = new ClasseDeToleranceDaoImpl();
		List<ClasseDeTolerance> listeClasseDeToleranceFiltree = new ArrayList<ClasseDeTolerance>();
		for (ClasseDeTolerance classeDeTolerance : classeDeToleranceDaoImpl.getAll(connexion)) {
			if (classeDeTolerance.getTypeClasseDeTolerance() == typeClasseDeTolerance) {
				listeClasseDeToleranceFiltree.add(classeDeTolerance);
			}
		}
		fermerConnexion();

		return listeClasseDeToleranceFiltree;

	}

	private static List<Tolerance> constructionListeTolerance(List<String> listeString) {
		List<String> listeValeurNormalisee = new ArrayList<String>();
		int nbEcartType = 0;
		while (!listeString.get(nbEcartType).equals("0")) {
			listeValeurNormalisee.add(listeString.get(nbEcartType));
			nbEcartType++;
		}

		List<Tolerance> listeTolerance = new ArrayList<Tolerance>();

		Integer auDela = null; // J'utilise Integer pour pouvoir avoir null
		Integer jusque = null;
		boolean ESTraite = false;
		Integer ES = null;
		Integer EI = null;
		int nbEcart = 0;
		int numValeurNormalisee = 0;

		for (int i = listeValeurNormalisee.size(); i < listeString.size(); i++) { // Je commence juste après le dernier
																					// écart type

			if (auDela == null) {
				auDela = Integer.valueOf(listeString.get(i));
			} else if (jusque == null) {
				jusque = Integer.valueOf(listeString.get(i));
			} else {
				nbEcart++;
				if (nbEcart <= (listeValeurNormalisee.size() * 2)) {
					if (!ESTraite) {
						try {
							ES = Integer.valueOf(listeString.get(i)); // Peut contenir '-'
						} catch (Exception e) {
							ES = null;
						}

						ESTraite = true;
					} else {
						try {
							EI = Integer.valueOf(listeString.get(i)); // Peut contenir '-'
						} catch (Exception e) {
							EI = null;
						}
						if (ES != null && EI != null) {

							ClasseDeTolerance classeDeTolerance = rechercherClasseDeTolerance(
									listeValeurNormalisee.get(numValeurNormalisee));

							Intervalle intervalle = rechercherIntervalle(auDela, jusque);

							Ecart ecart = rechercherEcart(BigDecimal.valueOf(ES), BigDecimal.valueOf(EI));

							Tolerance tolerance = new Tolerance(classeDeTolerance, intervalle, ecart);
							ToleranceDaoImpl toleranceDaoImpl = new ToleranceDaoImpl();
							toleranceDaoImpl.create(connexion, tolerance);

							listeTolerance.add(tolerance);
						}

						numValeurNormalisee++;
						ESTraite = false;
					}
					if (numValeurNormalisee == listeValeurNormalisee.size()) { // On vient de traiter le dernier
						nbEcart = 0;
						numValeurNormalisee = 0;
						auDela = jusque = null;
					}
				}
			}
		}

		return listeTolerance;
	}

	private static ClasseDeTolerance rechercherClasseDeTolerance(String codeClasseDeTolerance) {

		ClasseDeTolerance classeDeTolerance;
		ClasseDeToleranceDaoImpl classeDeToleranceDaoImpl = new ClasseDeToleranceDaoImpl();
		classeDeTolerance = classeDeToleranceDaoImpl.getByCodeClasseDeTolerance(connexion, codeClasseDeTolerance);

		if (classeDeTolerance == null) {
			classeDeTolerance = new ClasseDeTolerance(codeClasseDeTolerance);
			classeDeToleranceDaoImpl.create(connexion, classeDeTolerance);
		}

		return classeDeTolerance;

	}

	private static Intervalle rechercherIntervalle(int auDela, int jusque) {

		Intervalle intervalle;
		IntervalleDaoImpl intervalleDaoImpl = new IntervalleDaoImpl();
		intervalle = intervalleDaoImpl.getByAuDelaAndJusque(connexion, auDela, jusque);

		if (intervalle == null) {
			intervalle = new Intervalle(auDela, jusque);
			intervalleDaoImpl.create(connexion, intervalle);
		}

		return intervalle;
	}

	private static Ecart rechercherEcart(BigDecimal EcartSuperieur, BigDecimal EcartInferieur) {

		Ecart ecart;
		EcartDaoImpl ecartDaoImpl = new EcartDaoImpl();
		ecart = ecartDaoImpl.getByEcartSuperieurAndEcartInferieur(connexion, EcartSuperieur, EcartInferieur);

		if (ecart == null) {
			ecart = new Ecart(EcartSuperieur, EcartInferieur);
			ecartDaoImpl.create(connexion, ecart);
		}

		return ecart;

	}

	private static String tolerancesAlesages1() {

		String chaine = """
								A10 A11 A13 B8 B9 B10 B11 B13 C8 C10 C11 C12
				0 3 +295
				+270
				+330
				+270
				+410
				+270
				+154
				+140
				+165
				+140
				+180
				+140
				+200
				+140
				+280
				+140
				+74
				+60
				+100
				+60
				+120
				+60
				+160
				+60
				3 6 +300
				+270
				+345
				+270
				+450
				+270
				+158
				+140
				+170
				+140
				+188
				+140
				+215
				+140
				+320
				+140
				+88
				+70
				+118
				+70
				+145
				+70
				+190
				+70
				6 10 +316
				+280
				+370
				+280
				+500
				+280
				+172
				+150
				+186
				+150
				+208
				+150
				+240
				+150
				+370
				+150
				+102
				+80
				+138
				+80
				+170
				+80
				+230
				+80
				10 18 +333
				+290
				+400
				+290
				+560
				+290
				+177
				+150
				+193
				+150
				+220
				+150
				+260
				+150
				+420
				+150
				+122
				+95
				+165
				+95
				+205
				+95
				+275
				+95
				18 30 +352
				+300
				+430
				+300
				+630
				+300
				+193
				+160
				+212
				+160
				+244
				+160
				+290
				+160
				+490
				+160
				+143
				+110
				+194
				+110
				+240
				+110
				+320
				+110
				30 40 +372
				+310
				+470
				+310
				+700
				+310
				+209
				+170
				+232
				+170
				+270
				+170
				+330
				+170
				+560
				+170
				+159
				+120
				+220
				+120
				+280
				+120
				+370
				+120
				40 50 +382
				+320
				+480
				+320
				+710
				+320
				+219
				+180
				+242
				+180
				+280
				+180
				+340
				+180
				+570
				+180
				+169
				+130
				+230
				+130
				+290
				+130
				+380
				+130
				50 65 +414
				+340
				+530
				+340
				+800
				+340
				+236
				+190
				+264
				+190
				+310
				+190
				+380
				+190
				+650
				+190
				+186
				+140
				+260
				+140
				+330
				+140
				+440
				+140
				65 80 +434
				+360
				+550
				+360
				+820
				+360
				+246
				+200
				+274
				+200
				+320
				+200
				+390
				+200
				+660
				+200
				+196
				+150
				+270
				+150
				+340
				+150
				+450
				+150
				80 100 +467
				+380
				+600
				+380
				+920
				+380
				+274
				+220
				+307
				+220
				+360
				+220
				+440
				+220
				+760
				+220
				+224
				+170
				+310
				+170
				+390
				+170
				+520
				+170
				100 120 +497
				+410
				+630
				+410
				+950
				+410
				+294
				+240
				+327
				+240
				+380
				+240
				+460
				+240
				+780
				+240
				+234
				+180
				+320
				+180
				+400
				+180
				+530
				+180
				120 140 +560
				+460
				+710
				+460
				+1090
				+460
				+323
				+260
				+360
				+260
				+420
				+260
				+510
				+260
				+890
				+260
				+263
				+200
				+360
				+200
				+450
				+200
				+600
				+200
				140 160 +620
				+520
				+770
				+520
				+1150
				+520
				+343
				+280
				+380
				+280
				+440
				+280
				+530
				+280
				+910
				+280
				+273
				+210
				+370
				+210
				+460
				+210
				+610
				+210
				160 180 +680
				+580
				+830
				+580
				+1210
				+580
				+373
				+310
				+410
				+310
				+470
				+310
				+560
				+310
				+940
				+310
				+293
				+230
				+390
				+230
				+480
				+230
				+630
				+230
				180 200 +775
				+660
				+950
				+660
				+1380
				+660
				+412
				+340
				+455
				+340
				+525
				+340
				+630
				+340
				+1060
				+340
				+312
				+240
				+425
				+240
				+530
				+240
				+700
				+240
				200 225 +855
				+740
				+1030
				+740
				+1460
				+740
				+452
				+380
				+495
				+380
				+565
				+380
				+670
				+380
				+1100
				+380
				+332
				+260
				+445
				+260
				+550
				+260
				+720
				+260
				225 250 +935
				+820
				+1110
				+820
				+1540
				+820
				+492
				+420
				+535
				+420
				+605
				+420
				+710
				+420
				+1140
				+420
				+352
				+280
				+465
				+280
				+570
				+280
				+740
				+280
				250 280 +1050
				+920
				+1240
				+920
				+1730
				+920
				+561
				+480
				+610
				+480
				+690
				+480
				+800
				+480
				+1290
				+480
				+381
				+300
				+510
				+300
				+620
				+300
				+820
				+300
				280 315 +1180
				+1050
				+1370
				+1050
				+1860
				+1050
				+621
				+540
				+670
				+540
				+750
				+540
				+860
				+540
				+1350
				+540
				+411
				+330
				+540
				+330
				+650
				+330
				+850
				+330
				315 355 +1340
				+1200
				+1560
				+1200
				+2090
				+1200
				+689
				+600
				+740
				+600
				+830
				+600
				+960
				+600
				+1490
				+600
				+449
				+360
				+590
				+360
				+720
				+360
				+930
				+360
				355 400 +1490
				+1350
				+1710
				+1350
				+2240
				+1350
				+769
				+680
				+820
				+680
				+910
				+680
				+1040
				+680
				+1570
				+680
				+489
				+400
				+630
				+400
				+760
				+400
				+970
				+400
				400 450 +1655
				+1500
				+1900
				+1500
				+2470
				+1500
				+857
				+760
				+915
				+760
				+1010
				+760
				+1160
				+760
				+1730
				+760
				+537
				+440
				+690
				+440
				+840
				+440
				1070
				+440
				450 500 +1805
				+1650
				+2050
				+1650
				+2620
				+1650
				+937
				+840
				+995
				+840
				+1090
				+840
				+1240
				+840
				+1810
				+840
				+577
				+480
				+730
				+480
				+880
				+480
				1110
				+480
								""";
		return chaine.trim();
	}

	private static String tolerancesAlesages2() {
		String chaine = """
								R6 R7 R8 S7 S8 T7 T8 U7 U8 X7 X8 Z7
				0 3 -10
				-16
				-10
				-20
				-10
				-24
				-14
				-24
				-14
				-28
				- - - - -18
				-28
				-18
				-32
				-20
				-30
				-20
				-34
				-26
				-36
				3 6 -12
				-20
				-11
				-23
				-15
				-33
				-15
				-27
				-19
				-37
				- - - - -19
				-31
				-23
				-41
				-24
				-36
				-28
				-46
				-31
				-43
				6 10 -16
				-25
				-13
				-28
				-19
				-41
				-17
				-32
				-23
				-45
				- - - - -22
				-37
				-28
				-50
				-28
				-43
				-34
				-56
				-36
				-51
				10 14 -20
				-31
				-16
				-34
				-23
				-50
				-21
				-39
				-28
				-55
				- - - - -26
				-44
				-33
				-60
				-33
				-51
				-40
				-67
				-43
				-61
				14 18 -20
				-31
				-16
				-34
				-23
				-50
				-21
				-39
				-28
				-55
				- - - - -26
				-44
				-33
				-60
				-38
				-56
				-45
				-72
				-53
				-71
				18 24 -24
				-37
				-20
				-41
				-28
				-61
				-27
				-48
				-35
				-68
				- - - - -33
				-54
				-41
				-74
				-46
				-67
				-54
				-87
				-65
				-86
				24 30 -24
				-37
				-20
				-41
				-28
				-61
				-27
				-48
				-35
				-68
				-33
				-54
				-41
				-74
				-40
				-61
				-48
				-81
				-56
				-77
				-64
				-97
				-80
				-101
				30 40 -29
				-45
				-25
				-50
				-34
				-73
				-34
				-59
				-43
				-82
				-39
				-64
				-48
				-87
				-51
				-76
				-60
				-99
				-71
				-96
				-80
				-119
				-103
				-128
				40 50 -29
				-45
				-25
				-50
				-34
				-73
				-34
				-59
				-43
				-82
				-45
				-70
				-54
				-93
				-61
				-86
				-70
				-109
				-88
				-113
				-97
				-136
				-127
				-152
				50 65 -35
				-54
				-30
				-60
				-41
				-87
				-42
				-72
				-53
				-99
				-55
				-85
				-66
				-112
				-76
				-106
				-87
				-133
				-111
				-141
				-122
				-168
				-161
				-191
				65 80 -37
				-56
				-32
				-62
				-43
				-89
				-48
				-78
				-59
				-105
				-64
				-94
				-75
				-121
				-91
				-121
				-102
				-148
				-135
				-165
				-146
				-192
				-199
				-229
				80 100 -44
				-66
				-38
				-73
				-51
				-105
				-58
				-93
				-71
				-125
				-78
				-113
				-91
				-145
				-111
				-146
				-124
				-178
				-165
				-200
				-178
				-232
				-245
				-280
				100 120 -47
				-69
				-41
				-76
				-54
				-108
				-66
				-101
				-79
				-133
				-91
				-126
				-104
				-158
				-131
				-166
				-144
				-198
				-197
				-232
				-210
				-264
				-297
				-332
				120 140 -56
				-81
				-48
				-88
				-63
				-126
				-77
				-117
				-92
				-155
				-107
				-147
				-122
				-185
				-155
				-195
				-170
				-233
				-233
				-273
				-248
				-311
				-350
				-390
				140 160 -58
				-83
				-50
				-90
				-65
				-128
				-85
				-125
				-100
				-163
				-119
				-159
				-134
				-197
				-175
				-215
				-190
				-253
				-265
				-305
				-280
				-343
				-400
				-440
				160 180 -61
				-86
				-53
				-93
				-68
				-131
				-93
				-133
				-108
				-171
				-131
				-171
				-146
				-209
				-195
				-235
				-210
				-273
				-295
				-335
				-310
				-373
				-450
				-490
				180 200 -68
				-97
				-60
				-106
				-77
				-149
				-105
				-151
				-122
				-194
				-149
				-195
				-166
				-238
				-219
				-265
				-236
				-308
				-333
				-379
				-350
				-422
				-503
				-549
				200 225 -71
				-100
				-63
				-109
				-80
				-152
				-113
				-159
				-130
				-202
				-163
				-209
				-180
				-252
				-241
				-287
				-258
				-330
				-368
				-414
				-385
				-457
				-558
				-604
				225 250 -75
				-104
				-67
				-113
				-84
				-156
				-123
				-169
				-140
				-212
				-179
				-225
				-196
				-268
				-267
				-313
				-284
				-356
				-408
				-454
				-425
				-497
				-623
				-669
				250 280 -85
				-117
				-74
				-126
				-94
				-175
				-138
				-190
				-158
				-239
				-198
				-250
				-218
				-299
				-295
				-347
				-315
				-396
				-455
				-507
				-475
				-556
				-690
				-742
				280 315 -89
				-121
				-78
				-130
				-98
				-179
				-150
				-202
				-170
				-251
				-220
				-272
				-240
				-321
				-330
				-382
				-350
				-431
				-505
				-557
				-525
				-606
				-770
				-822
				315 355 -97
				-133
				-87
				-144
				-108
				-197
				-169
				-226
				-190
				-279
				-247
				-304
				-268
				-357
				-369
				-426
				-390
				-479
				-569
				-626
				-590
				-679
				-879
				-936
				355 400 -103
				-139
				-93
				-150
				-114
				-203
				-187
				-244
				-208
				-297
				-273
				-330
				-294
				-383
				-414
				-471
				-435
				-524
				-639
				-696
				-660
				-749
				-979
				-1036
				400 450 -113
				-153
				-103
				-166
				-126
				-223
				-209
				-272
				-232
				-329
				-307
				-370
				-330
				-427
				-467
				-530
				-490
				-587
				-717
				-780
				-740
				-837
				-1077
				-1140
				450 500 -119
				-159
				-109
				-172
				-132
				-229
				-229
				-292
				-252
				-349
				-337
				-400
				-360
				-457
				-517
				-580
				-540
				-637
				-797
				-860
				-820
				-917
				-1227
				-1290
												""";
		return chaine.trim();
	}

	private static String tolerancesArbres1() {
		String chaine = """
								a9 a11 a13 b8 b9 b10 b11 b13 c8 c10 c11 c12
				0 3 -270
				-295
				-270
				-330
				-270
				-410
				-140
				-154
				-140
				-165
				-140
				-180
				-140
				-200
				-140
				-280
				-60
				-74
				-60
				-100
				-60
				-120
				-60
				-160
				3 6 -270
				-300
				-270
				-345
				-270
				-450
				-140
				-158
				-140
				-170
				-140
				-188
				-140
				-215
				-140
				-320
				-70
				-88
				-70
				-118
				-70
				-145
				-70
				-190
				6 10 -280
				-316
				-280
				-370
				-280
				-500
				-150
				-172
				-150
				-186
				-150
				-208
				-150
				-240
				-150
				-370
				-80
				-102
				-80
				-138
				-80
				-170
				-80
				-230
				10 18 -290
				-333
				-290
				-400
				-290
				-560
				-150
				-177
				-150
				-193
				-150
				-220
				-150
				-260
				-150
				-420
				-95
				-122
				-95
				-165
				-95
				-205
				-95
				-275
				18 30 -300
				-352
				-300
				-430
				-300
				-630
				-160
				-193
				-160
				-212
				-160
				-244
				-160
				-290
				-160
				-490
				-110
				-143
				-110
				-194
				-110
				-240
				-110
				-320
				30 40 -310
				-372
				-310
				-470
				-310
				-700
				-170
				-209
				-170
				-232
				-170
				-270
				-170
				-330
				-170
				-560
				-120
				-159
				-120
				-220
				-120
				-280
				-120
				-370
				40 50 -320
				-382
				-320
				-480
				-320
				-710
				-180
				-219
				-180
				-242
				-180
				-280
				-180
				-340
				-180
				-570
				-130
				-169
				-130
				-230
				-130
				-290
				-130
				-380
				50 65 -340
				-414
				-340
				-530
				-340
				-800
				-190
				-236
				-190
				-264
				-190
				-310
				-190
				-380
				-190
				-650
				-140
				-186
				-140
				-260
				-140
				-330
				-140
				-440
				65 80 -360
				-434
				-360
				-550
				-360
				-820
				-200
				-246
				-200
				-274
				-200
				-320
				-200
				-390
				-200
				-660
				-150
				-196
				-150
				-270
				-150
				-340
				-150
				-450
				80 100 -380
				-467
				-380
				-600
				-380
				-920
				-220
				-274
				-220
				-307
				-220
				-360
				-220
				-440
				-220
				-760
				-170
				-224
				-170
				-310
				-170
				-390
				-170
				-520
				100 120 -410
				-497
				-410
				-630
				-410
				-950
				-240
				-294
				-240
				-327
				-240
				-380
				-240
				-460
				-240
				-780
				-180
				-234
				-180
				-320
				-180
				-400
				-180
				-530
				120 140 -460
				-560
				-460
				-710
				-460
				-1090
				-260
				-323
				-260
				-360
				-260
				-420
				-260
				-510
				-260
				-890
				-200
				-263
				-200
				-360
				-200
				-450
				-200
				-600
				140 160 -520
				-620
				-520
				-770
				-520
				-1150
				-280
				-343
				-280
				-380
				-280
				-440
				-280
				-530
				-280
				-910
				-210
				-273
				-210
				-370
				-210
				-460
				-210
				-610
				160 180 -580
				-680
				-580
				-830
				-580
				-1210
				-310
				-373
				-310
				-410
				-310
				-470
				-310
				-560
				-310
				-940
				-230
				-293
				-230
				-390
				-230
				-480
				-230
				-630
				180 200 -660
				-775
				-660
				-950
				-660
				-1380
				-340
				-412
				-340
				-455
				-340
				-525
				-340
				-630
				-340
				-1060
				-240
				-312
				-240
				-425
				-240
				-530
				-240
				-700
				200 225 -740
				-855
				-740
				-1030
				-740
				-1460
				-380
				-452
				-380
				-495
				-380
				-565
				-380
				-670
				-380
				-1100
				-260
				-332
				-260
				-445
				-260
				-550
				-260
				-720
				225 250 -820
				-935
				-820
				-1110
				-820
				-1540
				-420
				-492
				-420
				-535
				-420
				-605
				-420
				-710
				-420
				-1140
				-280
				-352
				-280
				-465
				-280
				-570
				-280
				-740
				250 280 -920
				-1050
				-920
				-1240
				-920
				-1730
				-480
				-561
				-480
				-610
				-480
				-690
				-480
				-800
				-480
				-1290
				-300
				-381
				-300
				-510
				-300
				-620
				-300
				-820
				280 315 -1050
				-1180
				-1050
				-1370
				-1050
				-1860
				-540
				-621
				-540
				-670
				-540
				-750
				-540
				-860
				-540
				-1350
				-330
				-411
				-330
				-540
				-330
				-650
				-330
				-850
				315 355 -1200
				-1340
				-1200
				-1560
				-1200
				-2090
				-600
				-689
				-600
				-740
				-600
				-830
				-600
				-960
				-600
				-1490
				-360
				-449
				-360
				-590
				-360
				-720
				-360
				-930
				355 400 -1350
				-1490
				-1350
				-1710
				-1350
				-2240
				-680
				-769
				-680
				-820
				-680
				-910
				-680
				-1040
				-680
				-1570
				-400
				-489
				-400
				-630
				-400
				-760
				-400
				-970
				400 450 -1500
				-1655
				-1500
				-1900
				-1500
				-2470
				-760
				-857
				-760
				-915
				-760
				-1010
				-760
				-1160
				-760
				-1730
				-440
				-537
				-440
				-690
				-440
				-840
				-440
				-1070
				450 500 -1650
				-1805
				-1650
				-2050
				-1650
				-2620
				-840
				-937
				-840
				-995
				-840
				-1090
				-840
				-1240
				-840
				-1810
				-480
				-577
				-480
				-730
				-480
				-880
				-480
				-1110
								""";
		return chaine.trim();
	}

	private static String tolerancesArbres2() {
		String chaine = """
								r6 r7 r8 s6 s7 t6 t7 u6 u7 x6 x7 z6
				0 3 +16
				+10
				+20
				+10
				+24
				+10
				+20
				+14
				+24
				+14
				- - +24
				+18
				+28
				+18
				+26
				+20
				+30
				+20
				+32
				+26
				3 6 +23
				+15
				+27
				+15
				+33
				+15
				+27
				+19
				+31
				+19
				- - +31
				+23
				+35
				+23
				+36
				+28
				+40
				+28
				+45
				+35
				6 10 +28
				+19
				+34
				+19
				+41
				+19
				+32
				+23
				+38
				+23
				- - +37
				+28
				+43
				+28
				+43
				+34
				+49
				+34
				+51
				+42
				10 14 +34
				+23
				+41
				+23
				+50
				+23
				39
				+28
				46
				+28
				- - +44
				+33
				+51
				+33
				+51
				+40
				+58
				+40
				+61
				+50
				14 18 +34
				+23
				+41
				+23
				+50
				+23
				39
				+28
				46
				+28
				- - +44
				+33
				+51
				+33
				+56
				+45
				+63
				+45
				+71
				+60
				18 24 +41
				+28
				+49
				+28
				+61
				+28
				+48
				+35
				+56
				+35
				- - +54
				+41
				+62
				+41
				+67
				+54
				+75
				+54
				+86
				+73
				24 30 +41
				+28
				+49
				+28
				+61
				+28
				+48
				+35
				+56
				+35
				+54
				+41
				+62
				+41
				+61
				+48
				+69
				+48
				+77
				+64
				+85
				+64
				+101
				+88
				30 40 +50
				+34
				+59
				+34
				+73
				+34
				+59
				+43
				+68
				+43
				+64
				+48
				+73
				+48
				+76
				+60
				+85
				+60
				+96
				+80
				+105
				+80
				+128
				+112
				40 50 +50
				+34
				+59
				+34
				+73
				+34
				+59
				+43
				+68
				+43
				+70
				+54
				+79
				+54
				+86
				+70
				+95
				+70
				+113
				+97
				+122
				+97
				+152
				+136
				50 65 +60
				+41
				+71
				+41
				+87
				+41
				+72
				+53
				+83
				+53
				+85
				+66
				+96
				+66
				+106
				+87
				+117
				+87
				+141
				+122
				+152
				+122
				+191
				+172
				65 80 +62
				+43
				+73
				+43
				+89
				+43
				+78
				+59
				+89
				+59
				+94
				+75
				+105
				+75
				+121
				+102
				+132
				+102
				+165
				+146
				+176
				+146
				+229
				+210
				80 100 +73
				+51
				+86
				+51
				+105
				+51
				+93
				+71
				+106
				+71
				+113
				+91
				+126
				+91
				+146
				+124
				+159
				+124
				+200
				+178
				+213
				+178
				+280
				+258
				100 120 +76
				+54
				+89
				+54
				+108
				+54
				+101
				+79
				+114
				+79
				+126
				+104
				+139
				+104
				+166
				+144
				+179
				+144
				+232
				+210
				+245
				+210
				+332
				+310
				120 140 +88
				+63
				+103
				+63
				+126
				+63
				+117
				+92
				+132
				+92
				+147
				+122
				+162
				+122
				+195
				+170
				+210
				+170
				+273
				+248
				+288
				+248
				+390
				+365
				140 160 +90
				+65
				+105
				+65
				+128
				+65
				+125
				+100
				+140
				+100
				+159
				+134
				+174
				+134
				+215
				+190
				+230
				+190
				+305
				+280
				+320
				+280
				+440
				+415
				160 180 +93
				+68
				+108
				+68
				+131
				+68
				+133
				+108
				+148
				+108
				+171
				+146
				+186
				+146
				+235
				+210
				+250
				+210
				+335
				+310
				+350
				+310
				+490
				+465
				180 200 +106
				+77
				+123
				+77
				+149
				+77
				+151
				+122
				+168
				+122
				+195
				+166
				+212
				+166
				+265
				+236
				+282
				+236
				+379
				+350
				+396
				+350
				+549
				+520
				200 225 +109
				+80
				+126
				+80
				+152
				+80
				+159
				+130
				+176
				+130
				+209
				+180
				+226
				+180
				+287
				+258
				+304
				+258
				+414
				+385
				+431
				+385
				+604
				+575
				225 250 +113
				+84
				+130
				+84
				+156
				+84
				+169
				+140
				+186
				+140
				+225
				+196
				+242
				+196
				+313
				+284
				+330
				+284
				+454
				+425
				+471
				+425
				+669
				+640
				250 280 +126
				+94
				+146
				+94
				+175
				+94
				+190
				+158
				+210
				+158
				+250
				+218
				+270
				+218
				+347
				+315
				+367
				+315
				+507
				+475
				+527
				+475
				+742
				+710
				280 315 +130
				+98
				+150
				+98
				+179
				+98
				+202
				+170
				+222
				+170
				+272
				+240
				+292
				+240
				+382
				+350
				+402
				+350
				+557
				+525
				+577
				+525
				+822
				+790
				315 355 +144
				+108
				+165
				+108
				+197
				+108
				+226
				+190
				+247
				+190
				+304
				+268
				+325
				+268
				+426
				+390
				+447
				+390
				+626
				+590
				+647
				+590
				+936
				+900
				355 400 +150
				+114
				+171
				+114
				+203
				+114
				+244
				+208
				+265
				+208
				+330
				+294
				+351
				+294
				+471
				+435
				+492
				+435
				+696
				+660
				+717
				+660
				1036
				1000
				400 450 +166
				+126
				+189
				+126
				+189
				+126
				+272
				+232
				+295
				+232
				+370
				+330
				+393
				+330
				+530
				+490
				+553
				+490
				+780
				+740
				+803
				+740
				1140
				1100
				450 500 +172
				+132
				+195
				+132
				+229
				+132
				+292
				+252
				+315
				+252
				+400
				+360
				+423
				+360
				+580
				+540
				+603
				+540
				+860
				+820
				+883
				+820
				1290
				1250
								""";
		return chaine.trim();
	}
}
