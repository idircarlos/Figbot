package behaviours;

import java.io.IOException;

import auxiliar.Constants;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import models.ActionData;

public class SendMessage extends CyclicBehaviour{
    
    private ActionData actionData;
    
    public SendMessage(Agent agent, ActionData actionData){
        super(agent);
        this.actionData = actionData;
    }
    
    @Override
    public void action() {
        if (actionData.getAction() == Constants.Code.ERROR) return;
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("visualizar-acciones");
        template.addServices(sd);
        AID[] processingAgents = null;
        try{
            DFAgentDescription[] result = DFService.search(myAgent, template);
            processingAgents = new AID[result.length];
            for(int i=0; i<result.length; i++){
                processingAgents[i] = result[i].getName();
            }
        } catch(FIPAException fe){
            fe.printStackTrace();
        }
        if(processingAgents == null || processingAgents.length == 0) return;
        try {
            for(int i = 0; i < processingAgents.length; i++){
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(processingAgents[i]);
                msg.setOntology("ontologia");
                msg.setLanguage(new SLCodec().getName());
                msg.setEnvelope(new Envelope());
                msg.getEnvelope().setPayloadEncoding("ISO8859_1");
                msg.setContentObject(actionData);
                
                myAgent.send(msg);
            }
            actionData.setAction(Constants.Code.ERROR);
        } catch (IOException e) {
            System.err.printf("No se pudo enviar el mensaje\n");
            e.printStackTrace();
        }
    }
}