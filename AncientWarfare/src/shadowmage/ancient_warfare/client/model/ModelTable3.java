//auto-generated model template
//template generated by MEIM
//template v 1.0
//author Shadowmage45 (shadowage_catapults@hotmail.com)
 
package shadowmage.ancient_warfare.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import shadowmage.ancient_warfare.common.crafting.TEAWCrafting;
 
 
public class ModelTable3 extends ModelTEBase
{
 
ModelRenderer tableTop;
ModelRenderer leg1;
ModelRenderer leg2;
ModelRenderer leg3;
ModelRenderer leg4;
ModelRenderer paperLarge;
ModelRenderer b1;
ModelRenderer b2;
ModelRenderer b3;
ModelRenderer b4;
ModelRenderer b5;
ModelRenderer b6;
ModelRenderer b7;
ModelRenderer b8;
ModelRenderer b9;
ModelRenderer b10;
ModelRenderer b11;
ModelRenderer b12;
ModelRenderer b13;
ModelRenderer b14;
ModelRenderer b15;
ModelRenderer b16;
ModelRenderer b17;
ModelRenderer b18;
ModelRenderer b19;
ModelRenderer b20;
ModelRenderer b21;
ModelRenderer b22;
ModelRenderer b23;
ModelRenderer b25;
ModelRenderer b27;
public ModelTable3(){
  tableTop = new ModelRenderer(this,"tableTop");
  tableTop.setTextureOffset(0,0);
  tableTop.setTextureSize(128,128);
  tableTop.setRotationPoint(0.0f, 0.0f, 0.0f);
  setPieceRotation(tableTop,0.0f, 0.0f, 0.0f);
  tableTop.addBox(-8.0f,-12.0f,-8.0f,16,1,16);
  leg1 = new ModelRenderer(this,"leg1");
  leg1.setTextureOffset(0,18);
  leg1.setTextureSize(128,128);
  leg1.setRotationPoint(0.0f, 0.0f, 0.0f);
  setPieceRotation(leg1,0.0f, 0.0f, 0.0f);
  leg1.addBox(-7.0f,-11.0f,5.0f,2,11,2);
  tableTop.addChild(leg1);
  leg2 = new ModelRenderer(this,"leg2");
  leg2.setTextureOffset(9,18);
  leg2.setTextureSize(128,128);
  leg2.setRotationPoint(0.0f, 0.0f, 0.0f);
  setPieceRotation(leg2,0.0f, 0.0f, 0.0f);
  leg2.addBox(-7.0f,-11.0f,-7.0f,2,11,2);
  tableTop.addChild(leg2);
  leg3 = new ModelRenderer(this,"leg3");
  leg3.setTextureOffset(18,18);
  leg3.setTextureSize(128,128);
  leg3.setRotationPoint(0.0f, 0.0f, 0.0f);
  setPieceRotation(leg3,0.0f, 0.0f, 0.0f);
  leg3.addBox(5.0f,-11.0f,-7.0f,2,11,2);
  tableTop.addChild(leg3);
  leg4 = new ModelRenderer(this,"leg4");
  leg4.setTextureOffset(27,18);
  leg4.setTextureSize(128,128);
  leg4.setRotationPoint(0.0f, 0.0f, 0.0f);
  setPieceRotation(leg4,0.0f, 0.0f, 0.0f);
  leg4.addBox(5.0f,-11.0f,5.0f,2,11,2);
  tableTop.addChild(leg4);
  paperLarge = new ModelRenderer(this,"paperLarge");
  paperLarge.setTextureOffset(65,0);
  paperLarge.setTextureSize(128,128);
  paperLarge.setRotationPoint(0.0f, -12.01f, 0.0f);
  setPieceRotation(paperLarge,0.0f, 0.087266594f, 0.0f);
  paperLarge.addBox(-6.0f,0.0f,-6.0f,12,0,12);
  b1 = new ModelRenderer(this,"b1");
  b1.setTextureOffset(36,18);
  b1.setTextureSize(128,128);
  b1.setRotationPoint(-4.5f, -2.5f, 4.5f);
  setPieceRotation(b1,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b1.addBox(-0.5f,-0.5f,-0.5f,10,1,1);
  paperLarge.addChild(b1);
  b2 = new ModelRenderer(this,"b2");
  b2.setTextureOffset(36,20);
  b2.setTextureSize(128,128);
  b2.setRotationPoint(-4.5f, -1.5f, -4.5f);
  setPieceRotation(b2,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b2.addBox(-0.5f,-1.5f,-0.5f,5,1,1);
  paperLarge.addChild(b2);
  b3 = new ModelRenderer(this,"b3");
  b3.setTextureOffset(0,32);
  b3.setTextureSize(128,128);
  b3.setRotationPoint(-4.5f, -2.5f, -3.5f);
  setPieceRotation(b3,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b3.addBox(-0.5f,-0.5f,-0.5f,1,1,8);
  paperLarge.addChild(b3);
  b4 = new ModelRenderer(this,"b4");
  b4.setTextureOffset(19,32);
  b4.setTextureSize(128,128);
  b4.setRotationPoint(4.5f, -2.5f, -3.5f);
  setPieceRotation(b4,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b4.addBox(-0.5f,-0.5f,-0.5f,1,1,8);
  paperLarge.addChild(b4);
  b5 = new ModelRenderer(this,"b5");
  b5.setTextureOffset(36,23);
  b5.setTextureSize(128,128);
  b5.setRotationPoint(1.5f, -2.5f, -4.5f);
  setPieceRotation(b5,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b5.addBox(-0.5f,-0.5f,-0.5f,4,1,1);
  paperLarge.addChild(b5);
  b6 = new ModelRenderer(this,"b6");
  b6.setTextureOffset(0,42);
  b6.setTextureSize(128,128);
  b6.setRotationPoint(-4.5f, -0.5f, -3.5f);
  setPieceRotation(b6,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b6.addBox(-0.5f,-0.5f,-0.5f,1,1,8);
  paperLarge.addChild(b6);
  b7 = new ModelRenderer(this,"b7");
  b7.setTextureOffset(49,20);
  b7.setTextureSize(128,128);
  b7.setRotationPoint(-4.5f, -1.5f, -4.5f);
  setPieceRotation(b7,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b7.addBox(-0.5f,-0.5f,-0.5f,1,1,2);
  paperLarge.addChild(b7);
  b8 = new ModelRenderer(this,"b8");
  b8.setTextureOffset(59,18);
  b8.setTextureSize(128,128);
  b8.setRotationPoint(-4.5f, -1.5f, 3.5f);
  setPieceRotation(b8,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b8.addBox(-0.5f,-0.5f,-0.5f,1,1,1);
  paperLarge.addChild(b8);
  b9 = new ModelRenderer(this,"b9");
  b9.setTextureOffset(56,21);
  b9.setTextureSize(128,128);
  b9.setRotationPoint(-4.5f, -1.5f, 0.5f);
  setPieceRotation(b9,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b9.addBox(-0.5f,-0.5f,-0.5f,1,1,1);
  paperLarge.addChild(b9);
  b10 = new ModelRenderer(this,"b10");
  b10.setTextureOffset(47,24);
  b10.setTextureSize(128,128);
  b10.setRotationPoint(-4.5f, -1.5f, -0.5f);
  setPieceRotation(b10,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b10.addBox(-0.5f,-0.5f,-0.5f,1,1,1);
  paperLarge.addChild(b10);
  b11 = new ModelRenderer(this,"b11");
  b11.setTextureOffset(36,27);
  b11.setTextureSize(128,128);
  b11.setRotationPoint(-4.5f, 0.5f, -4.5f);
  setPieceRotation(b11,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b11.addBox(-0.5f,-1.5f,-0.5f,5,1,1);
  paperLarge.addChild(b11);
  b12 = new ModelRenderer(this,"b12");
  b12.setTextureOffset(52,24);
  b12.setTextureSize(128,128);
  b12.setRotationPoint(-0.5f, -1.5f, -4.5f);
  setPieceRotation(b12,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b12.addBox(-0.5f,-0.5f,-0.5f,1,1,1);
  paperLarge.addChild(b12);
  b13 = new ModelRenderer(this,"b13");
  b13.setTextureOffset(57,24);
  b13.setTextureSize(128,128);
  b13.setRotationPoint(-3.5f, -1.5f, -4.5f);
  setPieceRotation(b13,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b13.addBox(-0.5f,-0.5f,-0.5f,1,1,1);
  paperLarge.addChild(b13);
  b14 = new ModelRenderer(this,"b14");
  b14.setTextureOffset(49,27);
  b14.setTextureSize(128,128);
  b14.setRotationPoint(1.5f, 0.5f, -4.5f);
  setPieceRotation(b14,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b14.addBox(-0.5f,-1.5f,-0.5f,4,1,1);
  paperLarge.addChild(b14);
  b15 = new ModelRenderer(this,"b15");
  b15.setTextureOffset(60,27);
  b15.setTextureSize(128,128);
  b15.setRotationPoint(1.5f, -1.5f, -4.5f);
  setPieceRotation(b15,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b15.addBox(-0.5f,-0.5f,-0.5f,1,1,1);
  paperLarge.addChild(b15);
  b16 = new ModelRenderer(this,"b16");
  b16.setTextureOffset(47,37);
  b16.setTextureSize(128,128);
  b16.setRotationPoint(4.5f, -1.5f, -4.5f);
  setPieceRotation(b16,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b16.addBox(-0.5f,-0.5f,-0.5f,1,1,2);
  paperLarge.addChild(b16);
  b17 = new ModelRenderer(this,"b17");
  b17.setTextureOffset(19,42);
  b17.setTextureSize(128,128);
  b17.setRotationPoint(4.5f, -0.5f, -3.5f);
  setPieceRotation(b17,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b17.addBox(-0.5f,-0.5f,-0.5f,1,1,8);
  paperLarge.addChild(b17);
  b18 = new ModelRenderer(this,"b18");
  b18.setTextureOffset(38,30);
  b18.setTextureSize(128,128);
  b18.setRotationPoint(4.5f, -1.5f, -0.5f);
  setPieceRotation(b18,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b18.addBox(-0.5f,-0.5f,-0.5f,1,1,2);
  paperLarge.addChild(b18);
  b19 = new ModelRenderer(this,"b19");
  b19.setTextureOffset(45,30);
  b19.setTextureSize(128,128);
  b19.setRotationPoint(4.5f, -1.5f, 3.5f);
  setPieceRotation(b19,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b19.addBox(-0.5f,-0.5f,-0.5f,1,1,1);
  paperLarge.addChild(b19);
  b20 = new ModelRenderer(this,"b20");
  b20.setTextureOffset(38,34);
  b20.setTextureSize(128,128);
  b20.setRotationPoint(-4.5f, -0.5f, 4.5f);
  setPieceRotation(b20,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b20.addBox(-0.5f,-0.5f,-0.5f,10,1,1);
  paperLarge.addChild(b20);
  b21 = new ModelRenderer(this,"b21");
  b21.setTextureOffset(50,30);
  b21.setTextureSize(128,128);
  b21.setRotationPoint(3.5f, -1.5f, -4.5f);
  setPieceRotation(b21,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b21.addBox(-0.5f,-0.5f,-0.5f,1,1,1);
  paperLarge.addChild(b21);
  b22 = new ModelRenderer(this,"b22");
  b22.setTextureOffset(55,30);
  b22.setTextureSize(128,128);
  b22.setRotationPoint(-4.5f, -1.5f, 4.5f);
  setPieceRotation(b22,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b22.addBox(-0.5f,-0.5f,-0.5f,3,1,1);
  paperLarge.addChild(b22);
  b23 = new ModelRenderer(this,"b23");
  b23.setTextureOffset(38,37);
  b23.setTextureSize(128,128);
  b23.setRotationPoint(2.5f, -1.5f, 4.5f);
  setPieceRotation(b23,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b23.addBox(-0.5f,-0.5f,-0.5f,3,1,1);
  paperLarge.addChild(b23);
  b25 = new ModelRenderer(this,"b25");
  b25.setTextureOffset(38,42);
  b25.setTextureSize(128,128);
  b25.setRotationPoint(-3.5f, -3.5f, -3.5f);
  setPieceRotation(b25,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b25.addBox(-0.5f,-0.5f,-0.5f,8,1,8);
  paperLarge.addChild(b25);
  b27 = new ModelRenderer(this,"b27");
  b27.setTextureOffset(60,27);
  b27.setTextureSize(128,128);
  b27.setRotationPoint(0.5f, -2.5f, -4.5f);
  setPieceRotation(b27,1.0402973E-9f, 1.0402973E-9f, 0.0f);
  b27.addBox(-0.5f,-0.5f,-0.5f,1,1,1);
  paperLarge.addChild(b27);

  tableTop.addChild(paperLarge);
  }
 
@Override
public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6)
  {
  super.render(entity, f1, f2, f3, f4, f5, f6);
  setRotationAngles(f1, f2, f3, f4, f5, f6, entity);
  tableTop.render(f6);
  }
 
public void setPieceRotation(ModelRenderer model, float x, float y, float z)
  {
  model.rotateAngleX = x;
  model.rotateAngleY = y;
  model.rotateAngleZ = z;
  }

@Override
public void renderModel(TEAWCrafting te)
  {
  tableTop.render(0.0625f);
  }

@Override
public void renderModel()
  {
  tableTop.render(0.0625f);
  }


}