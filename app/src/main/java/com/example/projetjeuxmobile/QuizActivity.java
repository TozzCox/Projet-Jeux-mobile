package com.example.projetjeuxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvScore, tvQuestionNo, tvTimer;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3;
    private Button btnNext;

    int totalQuestions;
    int qCounter = 0;
    int score;


    QuestionModel un =new QuestionModel("Quel champignon n’existe pas ?", "Le bolet de céléri", "La trompette-de-la-mort", "La barbe-de-bouc", 1);
    QuestionModel deux =new QuestionModel("L’art de cultiver les bonsaïs (ou arbres nains) est originaire :", "d’Afrique", "d’Indonésie", "du Japon", 3);
    QuestionModel troi =new QuestionModel("Comment appelle-t-on une grande pierre dressée ?", "Un menhir", "Un grohir", "Un lourhir", 1);
    QuestionModel quatre =new QuestionModel("Qu’est-ce qui doit arriver sur le stigmate pour que la fleur se reproduise ?", "Le pollen", "L’amidon", "La farine", 1);
    QuestionModel cinq =new QuestionModel("D’un iceberg flottant dans les régions polaires, on ne voit en fait :", "qu’un cinquième", "qu’un tiers", "que la moitié", 1);
    QuestionModel six =new QuestionModel("Combien de kilos de raisin faut-il pour faire 50 litres de vin ?", "100 kg", "50 kg", "250 kg", 1);
    QuestionModel sept =new QuestionModel("Le baobab est un des plus grands arbres du monde. Cet arbre est aussi appelé :", "arbre-mammouth", "arbre à pain de singe", "arbre géant", 2);
    QuestionModel huit =new QuestionModel("Lorsqu’un volcan se déchaine, on parle :", "d’érosion", "d’éruption", "d’équation", 2);
    QuestionModel neuf =new QuestionModel("Qu’est-ce qu’une météorite ?", "Une pierre dans l’espace", "Une boule de gaz", "Une accumulation de matière", 1);
    QuestionModel dix =new QuestionModel("Les bananes poussent :", "courbées vers le haut", "courbées vers le bas", "droites, elles se courbent après la récolte", 1);

    QuestionModel  [] list_game_nature = {un, deux, troi, quatre, cinq, six, sept, huit, neuf, dix};

    QuestionModel un_zero =new QuestionModel("Quels globules n’existent pas dans le corps humain ?", "Les globules rouges", "Les globules blancs", "Les globules violets", 3);
    QuestionModel un_un =new QuestionModel("Que fait le niveau de la mer lorsque la Terre se réchauffe ?", "il monte.", "Il baisse.", "Il reste identique.", 1);
    QuestionModel un_deux =new QuestionModel("De nombreux produits sont en cuir, mais d’où vient ce cuir ?", "De l’écorce de caoutchoutier", "Du pétrole", "De la peau des animaux", 3);
    QuestionModel un_troi =new QuestionModel("Dans quel organe du corps humain l’urine est-elle formée ?", "L’estomac", "Le rein", "L’intestin", 2);
    QuestionModel un_quatre =new QuestionModel("Le coeur humain se trouve dans la poitrine. Mais de quel côté ?", "À gauche", "Au milieu", "À droite", 1);
    QuestionModel un_cinq =new QuestionModel("Avec quel instrument un médecin écoute-t-il le rythme cardiaque ?", "Un cyclope", "Un périscope", "Un stéthoscope", 3);
    QuestionModel un_six =new QuestionModel("Combien de litres d’eau faut-il pour produire 1 tonne de papier ?", "40 000 litres", "4 000 litres", "400 litres", 1);
    QuestionModel un_sept =new QuestionModel("Quel matériau ne fait pas partie de la famille des carbones ?", "Le diamant", "L’asphalte", "La suie", 2);
    QuestionModel un_huit =new QuestionModel("Comment appelle-t-on la dentition des enfants ?", "Dents de lait", "Dents d’eau", "Dents de beurre", 1);
    QuestionModel un_neuf =new QuestionModel("Un four à micro-ondes réchauffe la nourriture :", "en faisant vibrer très vite la nourriture.", "en faisant vibrer les molécules d’eau de la nourriture.", "en produisant de la chaleur.", 2);

    QuestionModel  [] list_game_science = {un_un, un_deux, un_troi, un_quatre, un_cinq, un_six, un_sept, un_huit, un_neuf, un_zero};

    QuestionModel deux_zero =new QuestionModel("De combien de musiciens se compose un quartet ?", "4", "3", "5", 1);
    QuestionModel deux_un =new QuestionModel("Parmi ces trois langues, laquelle est la plus parlée", "Le français", "L’allemand", "L’espagnol.", 3);
    QuestionModel deux_deux =new QuestionModel("Si vous atterrissez à l’aéroport de Schiphol, c’est que vous êtes arrivés :", "en Afrique du Sud", "aux Pays-Bas", "aux Antilles néerlandaises", 2);
    QuestionModel deux_troi =new QuestionModel("Quelle est la capitale de l’Espagne ?", "Madrid", "Lisbonne", "Rome", 1);
    QuestionModel deux_quatre =new QuestionModel("Dans quel pays payait-on en lires avant l’apparition de l’euro ?", "En Espagne", "En Italie", "En Allemagne", 2);
    QuestionModel deux_cinq =new QuestionModel("De quel côté d’un bateau se trouve tribord ?", "À droite", "À gauche", "En arrière", 1);
    QuestionModel deux_six =new QuestionModel("De quel pays proviennent les spaghettis à l’origine ?", "De Turquie", "De Chine", "D’Italie", 2);
    QuestionModel deux_sept =new QuestionModel("Le TGV (train à grande vitesse) a été conçu en France et roule à une vitesse moyenne de :", "230 km/h", "350 km/h", "420 km/h", 1);
    QuestionModel deux_huit =new QuestionModel("La plus haute montagne d’Europe de l’Ouest est haute d’environ 4 807 mètres. Il s’agit du :", "Puy-de-Dôme", "mont Ventoux", "mont Blanc", 3);
    QuestionModel deux_neuf =new QuestionModel("Comment s’appelle l’organisation qui aide les enfants dans le monde entier ?", "Unicef", "Unesco", "Uno", 1);

    QuestionModel  [] list_game_monde = {deux_un, deux_deux, deux_troi, deux_quatre, deux_cinq, deux_six, deux_sept, deux_huit, deux_neuf, deux_zero};

    QuestionModel troi_zero =new QuestionModel("Les poulets fermiers sont des poulets qui :", "vivent dans un élevage", "peuvent se promener en liberté", "vivent dans une ville", 2);
    QuestionModel troi_un =new QuestionModel("Quel oiseau est un symbole international de paix ?", "La colombe", "L’hirondelle", "La cigogne", 1);
    QuestionModel troi_deux =new QuestionModel("L’élan est le plus grand cervidé du monde. En dehors de la saison de reproduction, il vit :", "en couple, avec les jeunes", "en solitaire", "en colonie", 2);
    QuestionModel troi_troi =new QuestionModel("Le crocodile reste parfois près de la rivière, immobile et la gueule grande ouverte. Pourquoi ?", "Pour être prêt à attraper un animal qui passerait par là.", "Pour inspirer beaucoup d’air avant d’aller sous l’eau.", "Pour perdre de la chaleur.", 3);
    QuestionModel troi_quatre =new QuestionModel("Le diodon est un poisson aux piquants acérés. Sous l’eau il a cette forme ronde :", "en permanence", "lorsqu’il se remplit d’eau", "lorsqu’il remonte près de la surface", 2);
    QuestionModel troi_cinq =new QuestionModel("Quelle espèce de rhinocéros n’existe pas ?", "Le rhinocéros blanc", "Le rhinocéros noir", "Le rhinocéros brun", 3);
    QuestionModel troi_six =new QuestionModel("Les poissons n’ont pas de poumons pour respirer. Avec quoi respirent-ils ?", "Leurs branchies", "Leurs écailles", "Leurs nageoires", 1);
    QuestionModel troi_sept =new QuestionModel("La libellule est un insecte que l’on rencontre surtout :", "Dans la montagne", "Dans la forêt", "Au bord de l’eau", 3);
    QuestionModel troi_huit =new QuestionModel("Où vit le renard polaire ?", "Au pôle Nord", "Au pôle Sud", "Au pôle Nord et au pôle Sud", 1);
    QuestionModel troi_neuf =new QuestionModel("Quel oiseau peut manger en volant ?", "Le merle", "Le colibri", "L’hirondelle", 2);

    QuestionModel  [] list_game_animaux = {troi_un, troi_deux, troi_troi, troi_quatre, troi_cinq, troi_six, troi_sept, troi_huit, troi_neuf, troi_zero};

    QuestionModel quatre_zero =new QuestionModel("Quelle discipline d’athlétisme n’est jamais courue aux Jeux olympiques ?", "Le sprint sur 100 mètres", "Les 110 mètres haies", "Les 100 mètres haies", 3);
    QuestionModel quatre_un =new QuestionModel("Combien pèse le poids lancé par les femmes dans le lancer du poids ?", "3 kg", "2 kg", "4 kg", 1);
    QuestionModel quatre_deux =new QuestionModel("D’où la marque de sport « Nike » tire-t-elle son nom ?", "D’une déesse grecque", "Du premier patron qui s’appelait Alfred Nike", "De Steve Nike qui fut le premier américain à gagner le 100 mètres", 1);
    QuestionModel quatre_troi =new QuestionModel("Un derby c’est une course de 2 400 m destinée aux chevaux de 3 ans. C’est aussi une compétition :", "opposant 2 équipes de la même ville ou de la même région", "entre des débutants d’une même discipline sportive", "opposant des équipes mixtes (hommes et femmes)", 1);
    QuestionModel quatre_cinq =new QuestionModel("Dans les Alpes suisses, on utilise une échelle spécifique pour indiquer le niveau de difficulté du parcours pour :", "Le ski", "Les promenades en montagne", "L’alpinisme", 3);
    QuestionModel quatre_six = new QuestionModel("Combien de joueurs y a-t-il sur le terrain durant un match de volley ?", "12", "8", "6", 1);
    QuestionModel quatre_sept =new QuestionModel("Un boxeur est déclaré K.O. après :", "5 secondes", "10 secondes", "15 secondes", 2);
    QuestionModel quatre_huit =new QuestionModel("De quel sport « la course contre la montre » fait-il partie ?", "La formule 1", "Le cyclisme", "L’équitation", 2);
    QuestionModel quatre_neuf =new QuestionModel("Que lance-t-on avec une raquette de badminton ?", "Un volant", "Une balle", "Une fléchette", 1);
    QuestionModel quatre_quatre =new QuestionModel("Où les boxeurs ne peuvent-ils pas frapper ?", "Sur le thorax", "Sur le visage", "Sous la ceinture", 3);

    QuestionModel  [] list_game_sport = {quatre_un, quatre_deux, quatre_troi, quatre_quatre, quatre_cinq, quatre_six, quatre_sept, quatre_huit, quatre_neuf, quatre_zero};


    ColorStateList dfRbColor;
    boolean answered;

    CountDownTimer countDownTimer;

    private QuestionModel currentQuestion;

    private List<QuestionModel> questionList;
    private List<QuestionModel> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);




        questionList = new ArrayList<>();
        tvQuestion = findViewById(R.id.textQuestion);
        tvScore = findViewById(R.id.texScore);
        tvQuestionNo = findViewById(R.id.textQuestionNo);
        tvTimer = findViewById(R.id.textTimer);

        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        btnNext = findViewById(R.id.btnNext);

        dfRbColor = rb1.getTextColors();

        //chooseQuestions();
        addQuestions();
        totalQuestions = questionList.size();
        showNextQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answered == false){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked()){
                        checkAnswer();
                        countDownTimer.cancel();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please Select an option", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });


    }
    private void checkAnswer() {
        answered = true;
        RadioButton rbSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNo = radioGroup.indexOfChild(rbSelected) + 1;
        if (answerNo == currentQuestion.getCorrectAnsNo()){
            score ++;
            tvScore.setText("Score: "+score);
        }
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        switch (currentQuestion.getCorrectAnsNo()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
        }
        if (qCounter < totalQuestions){
            btnNext.setText("Next");
        }else {
            btnNext.setText("Finish");
        }

    }

    private void showNextQuestion() {

        radioGroup.clearCheck();
        rb1.setTextColor(dfRbColor);
        rb2.setTextColor(dfRbColor);
        rb3.setTextColor(dfRbColor);

        if(qCounter < totalQuestions){
            timer();
            currentQuestion = questionList.get(qCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            qCounter++;
            btnNext.setText("Submit");
            tvQuestionNo.setText("Question: "+qCounter+"/"+totalQuestions);
            answered = false;
        }else {
            //finish();
            if (MainActivity.duel ==true){

                if(P2P.isHost){
                    MainActivity.duelScoreServer += score;
                }else{
                    MainActivity.duelScoreClient += score;
                }

                if (P2P.list_game.size()>0){
                    String current_game = P2P.list_game.get(0);
                    P2P.list_game.remove(current_game);


                    Class activity = null;

                    // Here, we are checking to see what the output of the random was
                    switch(current_game) {
                        case "1":

                            // E.g., if the output is 1, the activity we will open is ActivityOne.class
                            activity = Game1.class;
                            break;
                        case "2":
                            activity = Game2.class;
                            break;
                        case "3":
                            activity = Game3.class;
                            break;
                        case "4":
                            activity = Game4.class;
                            break;
                        case "5":
                            activity = Game5.class;
                            break;
                        case "6":
                            activity = Game6.class;
                            break;
                        default:
                            activity = MainActivity.class;
                            break;
                    }
                    // We use intents to start activities
                    Intent intent = new Intent(getBaseContext(), activity);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }else {
                finish();
            }
        }
    }

    private void timer() {
        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                tvTimer.setText("00:"+l/1000);
            }

            @Override
            public void onFinish() {
                showNextQuestion();
            }
        }.start();
    }



    private void addQuestions() {

        int first = (int)(Math.random() * 10) + 0;
        int second = (int)(Math.random() * 10) + 0;

        questionList.add(list_game_nature[first]);
        questionList.add(list_game_nature[second]);

        questionList.add(list_game_science[first]);
        questionList.add(list_game_science[second]);

        questionList.add(list_game_monde[first]);
        questionList.add(list_game_monde[second]);

        questionList.add(list_game_animaux[first]);
        questionList.add(list_game_animaux[second]);

        questionList.add(list_game_sport[first]);
        questionList.add(list_game_sport[second]);

    }

    private void chooseQuestions(){
        questions.add(new QuestionModel("Quel champignon n’existe pas ?", "Le bolet de céléri", "La trompette-de-la-mort", "La barbe-de-bouc", 1));
        questions.add(new QuestionModel("L’art de cultiver les bonsaïs (ou arbres nains) est originaire :", "d’Afrique", "d’Indonésie", "du Japon", 3));
        questions.add(new QuestionModel("Comment appelle-t-on une grande pierre dressée ?", "Un menhir", "Un grohir", "Un lourhir", 1));
        questions.add(new QuestionModel("Qu’est-ce qui doit arriver sur le stigmate pour que la fleur se reproduise ?", "Le pollen", "L’amidon", "La farine", 1));
        questions.add(new QuestionModel("D’un iceberg flottant dans les régions polaires, on ne voit en fait :", "qu’un cinquième", "qu’un tiers", "que la moitié", 1));
        questions.add(new QuestionModel("Combien de kilos de raisin faut-il pour faire 50 litres de vin ?", "100 kg", "50 kg", "250 kg", 1));
        questions.add(new QuestionModel("Le baobab est un des plus grands arbres du monde. Cet arbre est aussi appelé :", "arbre-mammouth", "arbre à pain de singe", "arbre géant", 2));
        questions.add(new QuestionModel("Lorsqu’un volcan se déchaine, on parle :", "d’érosion", "d’éruption", "d’équation", 2));
        questions.add(new QuestionModel("Qu’est-ce qu’une météorite ?", "Une pierre dans l’espace", "Une boule de gaz", "Une accumulation de matière", 1));
        questions.add(new QuestionModel("Les bananes poussent :", "courbées vers le haut", "courbées vers le bas", "droites, elles se courbent après la récolte", 1));


        questions.add(new QuestionModel("Quels globules n’existent pas dans le corps humain ?", "Les globules rouges", "Les globules blancs", "Les globules violets", 3));
        questions.add(new QuestionModel("Que fait le niveau de la mer lorsque la Terre se réchauffe ?", "il monte.", "Il baisse.", "Il reste identique.", 1));
        questions.add(new QuestionModel("De nombreux produits sont en cuir, mais d’où vient ce cuir ?", "De la peau des animaux", "Du pétrole", "De l’écorce de caoutchoutier", 1));
        questions.add(new QuestionModel("Dans quel organe du corps humain l’urine est-elle formée ?", "Le rein", "L’estomac", "L’intestin", 1));
        questions.add(new QuestionModel("Un four à micro-ondes réchauffe la nourriture :", "en faisant vibrer les molécules d’eau de la nourriture.", "en faisant vibrer très vite la nourriture.", "en produisant de la chaleur.", 1));
        questions.add(new QuestionModel("Le coeur humain se trouve dans la poitrine. Mais de quel côté ?", "À gauche", "Au milieu", "À droite", 1));
        questions.add(new QuestionModel("Avec quel instrument un médecin écoute-t-il le rythme cardiaque ?", "Un stéthoscope", "Un périscope", "Un cyclope", 1));
        questions.add(new QuestionModel("Combien de litres d’eau faut-il pour produire 1 tonne de papier ?", "40 000 litres", "4 000 litres", "400 litres", 1));
        questions.add(new QuestionModel("Quel matériau ne fait pas partie de la famille des carbones ?", "Le diamant", "L’asphalte", "La suie", 2));
        questions.add(new QuestionModel("Comment appelle-t-on la dentition des enfants ?", "Dents de lait", "Dents d’eau", "Dents de beurre", 1));

        questions.add(new QuestionModel("De combien de musiciens se compose un quartet ?", "4", "3", "5", 1));
        questions.add(new QuestionModel("Parmi ces trois langues, laquelle est la plus parlée", "Le français", "L’allemand", "L’espagnol.", 3));
        questions.add(new QuestionModel("Si vous atterrissez à l’aéroport de Schiphol, c’est que vous êtes arrivés :", "en Afrique du Sud", "aux Pays-Bas", "aux Antilles néerlandaises", 2));
        questions.add(new QuestionModel("Quelle est la capitale de l’Espagne ?", "Madrid", "Lisbonne", "Rome", 1));
        questions.add(new QuestionModel("Dans quel pays payait-on en lires avant l’apparition de l’euro ?", "En Italie", "En Espagne", "En Allemagne", 1));
        questions.add(new QuestionModel("De quel côté d’un bateau se trouve tribord ?", "À droite", "À gauche", "En arrière", 1));
        questions.add(new QuestionModel("De quel pays proviennent les spaghettis à l’origine ?", "De Chine", "De Turquie", "D’Italie", 1));
        questions.add(new QuestionModel("Le TGV (train à grande vitesse) a été conçu en France et roule à une vitesse moyenne de :", "230 km/h", "350 km/h", "420 km/h", 1));
        questions.add(new QuestionModel("La plus haute montagne d’Europe de l’Ouest est haute d’environ 4 807 mètres. Il s’agit du :", "mont Blanc", "mont Ventoux", "Puy-de-Dôme", 1));
        questions.add(new QuestionModel("Comment s’appelle l’organisation qui aide les enfants dans le monde entier ?", "Unicef", "Unesco", "Uno", 1));


        questions.add(new QuestionModel("Les poulets fermiers sont des poulets qui :", "vivent dans un élevage", "peuvent se promener en liberté", "vivent dans une ville", 2));
        questions.add(new QuestionModel("Quel oiseau est un symbole international de paix ?", "La colombe", "L’hirondelle", "La cigogne", 1));
        questions.add(new QuestionModel("L’élan est le plus grand cervidé du monde. En dehors de la saison de reproduction, il vit :", "en solitaire", "en couple, avec les jeunes", "en colonie", 1));
        questions.add(new QuestionModel("Le crocodile reste parfois près de la rivière, immobile et la gueule grande ouverte. Pourquoi ?", "Pour être prêt à attraper un animal qui passerait par là.", "Pour inspirer beaucoup d’air avant d’aller sous l’eau.", "Pour perdre de la chaleur.", 3));
        questions.add(new QuestionModel("Le diodon est un poisson aux piquants acérés. Sous l’eau il a cette forme ronde :", "en permanence", "lorsqu’il se remplit d’eau", "lorsqu’il remonte près de la surface", 2));
        questions.add(new QuestionModel("Quelle espèce de rhinocéros n’existe pas ?", "Le rhinocéros blanc", "Le rhinocéros noir", "Le rhinocéros brun", 3));
        questions.add(new QuestionModel("Les poissons n’ont pas de poumons pour respirer. Avec quoi respirent-ils ?", "Leurs branchies", "Leurs écailles", "Leurs nageoires", 1));
        questions.add(new QuestionModel("La libellule est un insecte que l’on rencontre surtout :", "Au bord de l’eau", "Dans la forêt", "Dans la montagne", 1));
        questions.add(new QuestionModel("Où vit le renard polaire ?", "Au pôle Nord", "Au pôle Sud", "Au pôle Nord et au pôle Sud", 1));
        questions.add(new QuestionModel("Quel oiseau peut manger en volant ?", "Le colibri", "Le merle", "L’hirondelle", 1));

        questions.add(new QuestionModel("Quelle discipline d’athlétisme n’est jamais courue aux Jeux olympiques ?", "Les 100 mètres haies", "Les 110 mètres haies", "Le sprint sur 100 mètres", 1));
        questions.add(new QuestionModel("Combien pèse le poids lancé par les femmes dans le lancer du poids ?", "3 kg", "2 kg", "4 kg", 1));
        questions.add(new QuestionModel("D’où la marque de sport « Nike » tire-t-elle son nom ?", "D’une déesse grecque", "Du premier patron qui s’appelait Alfred Nike", "De Steve Nike qui fut le premier américain à gagner le 100 mètres", 1));
        questions.add(new QuestionModel("Un derby c’est une course de 2 400 m destinée aux chevaux de 3 ans. C’est aussi une compétition :", "opposant 2 équipes de la même ville ou de la même région", "entre des débutants d’une même discipline sportive", "opposant des équipes mixtes (hommes et femmes)", 1));
        questions.add(new QuestionModel("Dans les Alpes suisses, on utilise une échelle spécifique pour indiquer le niveau de difficulté du parcours pour :", "Le ski", "Les promenades en montagne", "L’alpinisme", 3));
        questions.add(new QuestionModel("Combien de joueurs y a-t-il sur le terrain durant un match de volley ?", "12", "8", "6", 1));
        questions.add(new QuestionModel("Un boxeur est déclaré K.O. après :", "5 secondes", "10 secondes", "15 secondes", 2));
        questions.add(new QuestionModel("De quel sport « la course contre la montre » fait-il partie ?", "Le cyclisme", "La formule 1", "L’équitation", 1));
        questions.add(new QuestionModel("Que lance-t-on avec une raquette de badminton ?", "Un volant", "Une balle", "Une fléchette", 1));
        questions.add(new QuestionModel("Où les boxeurs ne peuvent-ils pas frapper ?", "Sous la ceinture", "Sur le visage", "Sur le thorax", 1));

    }
}