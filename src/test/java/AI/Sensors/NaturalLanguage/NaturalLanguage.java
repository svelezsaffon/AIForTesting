package AI.Sensors.NaturalLanguage;

/**
 * Created by santiago on 11/30/17.
 */

// Imports the Google Cloud client library

import AI.Actions.*;
import com.google.cloud.language.v1.*;
import com.google.cloud.language.v1.Document.Type;
import com.google.protobuf.Parser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NaturalLanguage {

    public Sentiment getDocumentSentiment(String text) throws IOException {

        LanguageServiceClient language = LanguageServiceClient.create();

        Document doc = Document.newBuilder()
                .setContent(text).setType(Type.PLAIN_TEXT).build();

        // Detects the sentiment of the text
        return language.analyzeSentiment(doc).getDocumentSentiment();
    }


    public Action buildActionUpponText(String text) throws Exception {

        LanguageServiceClient language = LanguageServiceClient.create();

        Document doc = Document.newBuilder()
                .setContent(text).setType(Type.PLAIN_TEXT).build();
/*
        AnalyzeSyntaxRequest request = AnalyzeSyntaxRequest.newBuilder()
                .setDocument(doc)
                .setEncodingType(EncodingType.UTF16)
                .build();
        AnalyzeEntitiesRequest requestEntities=AnalyzeEntitiesRequest.newBuilder().setDocument(doc).setEncodingType(EncodingType.UTF16)
                .build();
*/
        AnnotateTextRequest.Features features = AnnotateTextRequest.Features.newBuilder().setExtractEntities(true).setExtractSyntax(true).build();
        EncodingType encodingType = EncodingType.UTF16;

        AnnotateTextRequest request = AnnotateTextRequest.newBuilder()
                .setDocument(doc)
                .setFeatures(features)
                .setEncodingType(encodingType)
                .build();

        AnnotateTextResponse response=language.annotateText(request);

        // analyze the syntax in the given text
        List<Token> syntax = response.getTokensList();

        List<Entity> entities = response.getEntitiesList();

        Action result=this.buildActionUponResponse(response);

        return result;
    }


    private Action buildActionUponResponse(AnnotateTextResponse response) throws Exception{
        List<Token> rootVerbs=response.getTokensList().parallelStream().filter(token -> token.getPartOfSpeech().getTag() == PartOfSpeech.Tag.VERB).collect(Collectors.toList()).
                parallelStream().filter(token -> token.getDependencyEdge().getLabel() == DependencyEdge.Label.ROOT).collect(Collectors.toList());

        if(rootVerbs.isEmpty()){
            throw new NoRootActionDetected("No root action detected");
        }

        Token token=rootVerbs.get(0);


        Action result=null;

        //TODO this should be more intelligent, but we could do it for now
        //TODO Build a tensor flow neural network to detect these words
        String visitArray[]={"Visit","Go to","go to","visit"};
        List<String> visit = Arrays.asList(visitArray);

        String clickArray[]={"Click","click"};
        List<String> click = Arrays.asList(clickArray);

        String typeArray[]={"Type","type","Write","write"};
        List<String> type = Arrays.asList(typeArray);

        String lema= token.getLemma();
        if(visit.contains(lema)){
            result= new Visit(lema);
        }else if(click.contains(lema)){
            result=new Click(lema);
        }else if(type.contains(lema)){
            result =new Write(lema);
        }


        result.create(response);


        return result;
    }





}
