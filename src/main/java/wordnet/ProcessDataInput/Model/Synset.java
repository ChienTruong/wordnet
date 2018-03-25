package wordnet.ProcessDataInput.Model;

import wordnet.Util.Regex;
import wordnet.Util.SetOfWordAbsolutelyNotIsNoun;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 15/03/2018.
 */
public class Synset {
    private String synsetId;
    private String gloss;
    private Map<String, WordForm> mapWordForm = new HashMap<>(0);
    private Map<String, Synset> mapSynsetLayerOnes = new HashMap<>(0);
    private Map<String, WordForm> mapWordFormFromGloss = new HashMap<>(0);
    private Set<String> allWordInGloss;
    private Map<String, String> mapReflectionBetweenWordInGlossAndSynsetId;
    private Map<String, String> mapReflectonBetweenWordAndThisId;

    public String getSynsetId() {
        return synsetId;
    }

    public void setSynsetId(String synsetId) {
        this.synsetId = synsetId;
    }

    public String getGloss() {
        return gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public Map<String, WordForm> getMapWordForm() {
        return mapWordForm;
    }

    public void setMapWordForm(Map<String, WordForm> mapWordForm) {
        this.mapWordForm = mapWordForm;
    }

    public Map<String, Synset> getMapSynsetLayerOnes() {
        return mapSynsetLayerOnes;
    }

    public void setMapSynsetLayerOnes(Map<String, Synset> mapSynsetLayerOnes) {
        this.mapSynsetLayerOnes = mapSynsetLayerOnes;
    }

    public Map<String, WordForm> getMapWordFormFromGloss() {
        return mapWordFormFromGloss;
    }

    public void setMapWordFormFromGloss(Map<String, WordForm> mapWordFormFromGloss) {
        this.mapWordFormFromGloss = mapWordFormFromGloss;
    }

    public Map<String, String> getMapReflectonBetweenWordAndThisId() {
        if (this.mapReflectonBetweenWordAndThisId == null) {
            this.mapReflectonBetweenWordAndThisId = new HashMap<>(0);
            this.getMapWordForm().forEach(
                    (s, wordForm) -> {
                        this.mapReflectonBetweenWordAndThisId.put(s, this.synsetId);
                    }
            );
        }
        return this.mapReflectonBetweenWordAndThisId;
    }

    public Map<String, String> getMapReflectionBetweenWordInGlossAndSynsetId() {
        if (this.mapReflectionBetweenWordInGlossAndSynsetId == null) {
            this.retrieveWordFromGloss();
        }
        return this.mapReflectionBetweenWordInGlossAndSynsetId;
    }

    public Set<String> getAllWordInGloss() {
        if (this.allWordInGloss == null) {
            this.allWordInGloss = new HashSet<>(0);
            if (this.mapReflectionBetweenWordInGlossAndSynsetId == null) {
                this.retrieveWordFromGloss();
            }
            this.allWordInGloss.addAll(this.mapReflectionBetweenWordInGlossAndSynsetId.keySet());
        }
        return this.allWordInGloss;
    }

    private void retrieveWordFromGloss() {
        this.mapReflectionBetweenWordInGlossAndSynsetId = new HashMap<>(0);
        Set<String> setWordOfGloss = this.removeWordForGloss(this.gloss);
        for (String wordOfGloss : setWordOfGloss) {
            this.mapReflectionBetweenWordInGlossAndSynsetId.put(wordOfGloss, this.synsetId);
        }
    }

    private Set<String> removeWordForGloss(String gloss) {
        Set<String> setWordOfGloss = new HashSet<>(0);
        if (gloss != null) {
            String[] strings = gloss.replaceAll(Regex.regexAllNonWordCharacter, " ").split(" ");
            for (String string : strings) {
                if (!SetOfWordAbsolutelyNotIsNoun.setWordNotIsNoun.contains(string)) {
                    setWordOfGloss.add(string);
                }
            }
        }
        return setWordOfGloss;
    }

    @Override
    public String toString() {
        return "Synset{" +
                "synsetId='" + synsetId + '\'' +
                ", gloss='" + gloss + '\'' +
                ", mapWordForm=" + mapWordForm +
                ", mapSynsetLayerOnes=" + mapSynsetLayerOnes +
                ", mapWordFormFromGloss=" + mapWordFormFromGloss +
                '}';
    }
}
