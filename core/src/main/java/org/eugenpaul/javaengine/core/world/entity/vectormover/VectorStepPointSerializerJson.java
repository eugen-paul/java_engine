package org.eugenpaul.javaengine.core.world.entity.vectormover;

public interface VectorStepPointSerializerJson {

}

//public class VectorStepPointSerializerJson extends StdSerializer<VectorMover> {
//
//  /**
//   * 
//   */
//  private static final long serialVersionUID = -4928007635507133727L;
//
//  protected VectorStepPointSerializerJson(Class<VectorMover> t) {
//    super(t);
//  }
//
//  @Override
//  public void serialize(VectorMover mover, JsonGenerator gen, SerializerProvider provider) throws IOException {
//    gen.writeStartObject();
//
//    gen.writeArrayFieldStart("movingVectors");
//    List<MovingParams> movingVector = mover.getMovingVectors();
//    for (MovingParams movingData : movingVector) {
//      gen.writeStartObject();
//      gen.writeNumberField("ID", movingData.getVector().getId());
//
//      gen.writeArrayFieldStart("steps");
//      for (Step steps : movingData.getSteps()) {
//
//      }
//      gen.writeEndArray();
//
//      gen.writeEndObject();
//    }
//    gen.writeEndArray();
//
//    gen.writeEndObject();
//  }
//}
