package com.helios.helios.objects.vote;

import com.helios.helios.objects.election.Election;
import com.helios.helios.objects.election.ElgamalPublicKey;
import com.helios.helios.objects.vote.ElgamalCiphertext;
import com.helios.service.Crypto;
import com.helios.service.HttpJsonReader;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by kepuss on 16.05.15.
 */
@JsonPropertyOrder({ "A", "B", "choice_num", "ciphertext", "election_hash", "question_num" })
public class Challenge {
    private String A;
    private String B;
    private int choice_num;
    private ElgamalCiphertext ciphertext;
    private String election_hash;
    private int question_num;
    private BigInteger w;


    public Challenge(Election el, String message) throws IOException, NoSuchAlgorithmException {
        ElgamalPublicKey pub = el.getPublic_key();
        Random random = new Random();
        w = BigInteger.valueOf(random.nextInt(9000000));
        A = new BigInteger(pub.getG()).modPow(w, new BigInteger(pub.getP())).toString();
        B = new BigInteger(pub.getY()).modPow(w, new BigInteger(pub.getP())).toString();

  //      A="6375953430131296113113850984805261496898661140879578981807664907854639433133684359206908365656245867915319685313268516575699614369913985095807148137568056255920174615624433254806748217439122869880137164175073393795023189789812633994744718948545059040692673480665822138591415826733150869882118806632099319733947812248742362662115639433248636532831441993246026035382484035176364566340299295764384562269606882795958968261315494717438914647604262639065563971257258758823224941824556226912812053966696690925786112501759893618715067580019063503952981478524006299495128454152296845253320782235421274423417243642856414696288";
  //      B="16177204694801358484446637369927454017597121428198659543989490851780760243738473301055885533198617121814055141655885625016352267324655127640517716837642828569103272099941495755336146896686661822526842611965361749844486689572035632711638883813427562324617248099431420385855805602098130946582454470663349610999092806481823328342398030871313546450165533280024124801456666696616771923972303473784843256019888107845528080853263853411140870932018104065118843787880219826287757963365074271176655574163565197588872236792186888881875754012985889524060370412443221695243875457669928340534713763429395952477719027402450322362541";

        HttpJsonReader<Election> reader = new HttpJsonReader<>();

        choice_num =0;
        election_hash = Crypto.sha256AndBase64(reader.toJson(el));
        election_hash = election_hash.substring(0, election_hash.length() - 1);
        question_num =0;

      //  ciphertext = new ElgamalCiphertext(pub,message);
    }

    @JsonProperty("A")
    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }
    @JsonProperty("B")
    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public int getChoice_num() {
        return choice_num;
    }

    public void setChoice_num(int choice_num) {
        this.choice_num = choice_num;
    }

    public ElgamalCiphertext getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(ElgamalCiphertext ciphertext) {
        this.ciphertext = ciphertext;
    }

    public String getElection_hash() {
        return election_hash;
    }

    public void setElection_hash(String election_hash) {
        this.election_hash = election_hash;
    }

    public int getQuestion_num() {
        return question_num;
    }

    public void setQuestion_num(int question_num) {
        this.question_num = question_num;
    }

    @JsonIgnore
    public BigInteger getW() {
        return w;
    }
    @JsonIgnore
    public void setW(BigInteger w) {
        this.w = w;
    }
}
