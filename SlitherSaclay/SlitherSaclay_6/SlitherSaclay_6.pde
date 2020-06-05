// Questions :
/*
//test2
Q1 : dessiner joli segment rond pour serpent
Q2 : dessiner visage serpent (yeux, langue, etc.)
Q3 : materialiser bordure de scroll (carre)
 Q4 : dessiner joli "noisette" ou "gland" pour nourriture
 Q5 : dessiner un terrain selon perlin
 --------------------------------------
 Note de projet No1 pour le 5 ou 6 decembre
 
 Q6 : dessiner serpent "lumineux" quand "acceleration" avec la touche espace ============ LIGNE 182 ============
 Q7 : animation a la mort (mieux que le game-over qui rapetisse) ======================= LIGNE 355 ==============
 Q8 : animation au repas
 Q9 : dessiner l'ombre d'un quadricoptere au centre du plateau de jeu =============== LIGNE 488 ============
 Q10 : tableau des vainqueurs
 --------------------------------------
 Note de projet No2 pour la soutenance finale debut janvier
*/



// variables globales

int score = 0;
String[] nomsClassementIn={"","","","","",""};
String nomsClassementInTemp="";
int[] scoresClassementIn={0,0,0,0,0,0};
int scoresClassementInTemp=0;
String[] nomsClassement={"a","a","a","a","a","a","a","a","a","a",""}; 
String nomsClassementTemp="";
int[] scoresClassement={0,1,2,3,4,5,6,7,8,9,0};
int scoresClassementTemp=0;
int placeNom=-1;
int placeTableau=0;
boolean afficherTableau=false;
String nom="";
//---------------------------------------
PImage img1,img2;
boolean merge = false;
Snake snakes[];
int TerrX     = 40;
int TerrY     = 40;

int gaussR = 11;
float gauss[][];
int panX = 0;
int panY = 0;

int state = 1;

int BORDER_SIZE = 200;
String names[]={"Fred", "Nicolas", "Yacine", "Olivia", "Medhi", "Christian", "Laura"};
 
ArrayList foods; 
 
// on regroupe les variables d'un serpent dans une structure
class Snake {
  ArrayList pos;
  int size = 16;
  int weight = 160;
  float dirX = 10;
  float dirY = 0;
  float speed = 10;
  float r, g, b;
  String name;
  int colfood;
  float rTemp;
  float gTemp;
  float bTemp;
  int score;
  Snake(String name0,int size0, int x0, int y0, int r0, int g0, int b0, float dirX0, float dirY0){
    r=r0;
    g=g0; 
    b=b0;
    rTemp=r;
    gTemp=g;
    bTemp=b;
    
    name = name0;
    dirX = dirX0;
    dirY = dirY0;
    colfood = 0;
    
    size = size0;
    pos = new ArrayList();
    for (int i = size; i>=0; i--){
      Point s = new Point(x0+i*dirX, y0+i*dirY);
      pos.add(s);
    }
    setWeight(size*10);
    score=weight-30;
  }
  
  void setWeight(int weight1){
    weight = weight1;
    for (int i = size; i>=0; i--){
      Point s = (Point)pos.get(i);
      s.r = 10+sqrt((weight-39))/4.0;
      if (i==1) s.r-=2;
      if (i==2) s.r--;
    }
    
    int acc = 8;
    for (int i = pos.size()-1; i>=3; i--){
      Point s  = (Point)pos.get(i);
      if (acc<s.r)
        s.r = acc;
      acc+=1;
    }
    score=weight-30;
  }
  
}
 
 
// une autre structure pour representer les segments de serpent ou les boules de nourriture 
class Point{
  float x; 
  float y;
  float r;
  Point (float x0, float y0){
    x = x0;
    y = y0;
    r = 10;
  }
}


Point newFood(){
  float a = random(0,10000)*PI/5000;
  float d = random(0,5000);
  
  Point p = new Point(d*cos(a), d*sin(a));
  p.r = random(1,5);
  return p;
}



void setup() {  // this is run once.       
    size(1024, 1024); 
    snakes = new Snake[6]; 
    for (int k=1; k<snakes.length; k++){
      snakes[k] = new Snake(names[k], 4+10*(k-1), width/2+(int)(300*cos(k*PI/3)), height/2+(int)(300*sin(k*PI/3)), 128+64*(k%3), 255*(k%2), 0, 10*cos((k+2)*PI/3), 10*sin((k+2)*PI/3));
    }
    
    foods = new ArrayList();
    for (int k=0; k<1000; k++){
      foods.add(newFood());
    }
    
    gauss = new float[gaussR][gaussR];
    for (int k=0; k<gaussR; k++)
      for (int l=0; l<gaussR; l++)
        gauss[k][l] = exp(-1.0/gaussR/gaussR*((k-gaussR/2)*(k-gaussR/2)+(l-gaussR/2)*(l-gaussR/2)));
     
    // 2 images
    img1 = createImage(64, 64, ARGB);
    img2 = createImage(64+gaussR, 64+gaussR, ARGB);

    
    // Q1 un dessin de cercle dans la premiere image
    colorMode(HSB,100); 
    for(int j=0; j < img1.height; j++) {
      for(int i=0; i < img1.width; i++) {
        float d0 = dist(i,j,img1.width/2,img1.height/2);
        if (d0<img1.width/2-1){
          
          img1.pixels[i+j*img1.width] = color(50,0,30+i); 
        }
      }
    }colorMode(RGB,255);
    
    // l'ombre du cercle dans la 2ieme image
    for(int j=0; j < img1.height; j++) {
      for(int i=0; i < img1.width; i++) {
        float c = alpha(img1.pixels[i+j*img1.width]);
        for (int k=0; k<gaussR; k++){
          for (int l=0; l<gaussR; l++){
            img2.pixels[i+l+(j+k)*img2.width] += c*gauss[k][l]; 
          }
        }
      }
    }
    int ma = img2.pixels[img2.width/2+img2.height/2*img2.width];
    for(int j=0; j < img2.height; j++) {
      for(int i=0; i < img2.width; i++) {
        int value= img2.pixels[i+j*img2.width];
        img2.pixels[i+j*img2.width] = color(0, 128*value/ma);
      }
    }
    
    img1.updatePixels();    
    img2.updatePixels();
    
    frameRate(25);
} 


void moveSnake(Snake s, float objx, float objy){
  Point p0 = (Point) s.pos.get(0);
  Point pn = (Point) s.pos.get(s.pos.size()-1);
  
  float dd = dist(p0.x, p0.y, objx, objy);
  float tx = objx;
  float ty = objy;
  if (dd>s.speed){
    tx = p0.x+(objx-p0.x)/dd*s.speed;
    ty = p0.y+(objy-p0.y)/dd*s.speed;
  }
  if (dd>0){
    s.dirX = (objx-p0.x)/dd*s.speed;
    s.dirY = (objy-p0.y)/dd*s.speed;
  }
  
  // si on va "vite" on seme de la nourriture derriere soi et on perd du poids
  if(s.speed>10){
    s.setWeight(s.weight -2);
    Point f = new Point(pn.x, pn.y);
    f.r = 1;
    foods.add(f);
      
    if (s.weight<10*s.size){
      s.size--;
      s.pos.remove(s.pos.size()-1);
      
    }
    if (s.weight<42){
      s.speed=10;
    }
  }
  
  // deplace chaque segment du serpent
  float tlen = 0;
  for (int i = 0; i<s.pos.size(); i++){
    Point p = (Point) s.pos.get(i);
    float len = dist(p.x, p.y, tx, ty);
    if (len > tlen){
      p.x = tx-(tx-p.x)*tlen/len;
      p.y = ty-(ty-p.y)*tlen/len;
    }
    tx = p.x;
    ty = p.y;
    tlen = 10; 
  }
}

 
boolean testCollision(Snake s){
  Point p0 = (Point)s.pos.get(0);
  Point pn = (Point)s.pos.get(s.pos.size()-1);
  
  for (int i=0; i<snakes.length; i++){
    Snake other = snakes[i];
    if (other!=null && other!=s){
      for(int k=0; k<other.pos.size(); k++){
        Point p = (Point)other.pos.get(k);
        float dd = dist(p.x, p.y, p0.x, p0.y);
        if (dd<p0.r+p.r){
            return true;
        }
      }
    }
  }
  
  for (int k=foods.size()-1; k>=0; k--){
    Point f = (Point)foods.get(k);
    float dd = dist(p0.x, p0.y, f.x, f.y);
    if (dd < p0.r+f.r) {
      s.setWeight(s.weight +(int)f.r);
      s.colfood = 100;
      
      
      
      
      if (s.weight>10*s.size){
        s.size++;
        Point p = new Point(pn.x, pn.y);
        p.r= 8;
        s.pos.add(p);
      }
    
      foods.remove(k);
    }
  }
  return false;
}
 
 
void drawSnake(Snake s) {
  
  for (int i = s.pos.size()-1; i >= 0; i--){
    Point p = (Point) s.pos.get(i);
    if (s.colfood > 0)
       s.colfood--;
    pushMatrix();
    translate( p.x-panX, p.y-panY);
    if (i==0){
      rotate(atan2(s.dirY, s.dirX));
      
      
      //Q2 dessin dessous
      
      stroke(255,25,25);
      strokeWeight(2);
      if (s.colfood > 0)
      {
      line(0,0,6+log(s.size)*4,0);
      line(6+log(s.size)*4,0,13+log(s.size)*4,-(2+log(s.size)));
      line(6+log(s.size)*4,0,13+log(s.size)*4,2+log(s.size));
      colorMode(HSB);
      for(int k=0; k<foods.size(); k++){
        s.r=k/5*(frameCount*6)%155;
        s.g=k/2*(frameCount*6)%25;
        s.b=k/2*(frameCount*6)%155;
      }
      colorMode(RGB);
    
      }else{
      line(0,0,6+log(s.size)*10,0);
      line(6+log(s.size)*10,0,13+log(s.size)*10,-(2+log(s.size)));
      line(6+log(s.size)*10,0,13+log(s.size)*10,2+log(s.size));
      s.r=s.rTemp;
      s.g=s.gTemp;
      s.b=s.bTemp;
      
      }
      stroke(0);
      strokeWeight(0.8);
      
      pushMatrix();
      scale(2.2*p.r/img1.width, 2.0*p.r/img1.width);
    } else 
      scale(2.0*p.r/img1.width);
   
    if (i%2==0){
      if (s.speed>10){
        //image(img3, 0,0);
        tint(s.r, s.g, s.b);
        image(img1, 0,0);
        noTint();
      }else{
        //image(img2, 0,0);
        tint(s.r/2, s.g/2, s.b/2);
        image(img1, 0,0);
        noTint();
      }
    } else {
      if (s.speed>10){
        //image(img3, 0,0);
        tint(255-(255-s.r)/2, 255-(255-s.g)/2, 255-(255-s.b)/2);
        image(img1, 0,0);
        noTint();
      }else{
        //image(img2, 0,0);
        tint(s.r, s.g, s.b);
        image(img1, 0,0);
        noTint();
      }

    }
    
    if (i==0){
      popMatrix();
      //Q2 dessin dessus
      fill(255,255,255);
      ellipse(3,5  ,(log(s.size)*3),(-log(s.size)*3));
      ellipse(3,-5  ,(log(s.size)*3),(-log(s.size)*3));
      fill(255,128,128);
      ellipse(5,5  ,log(s.size),-log(s.size));
      ellipse(5,-5  ,log(s.size),-log(s.size));
    }
    popMatrix();
  }
  
  
  Point p = (Point) s.pos.get(0);  
  fill(0);
  textSize(14);
  text(s.name+" "+(s.weight-30),  p.x-panX, p.y-panY);
  fill(255);
  text(s.name+" "+(s.weight-30),  p.x-panX-1, p.y-panY-1);
}
 
// Q5
void drawBg(){
  background(255,192,128);
  noStroke();
  
  for (int j=-panY%20-20; j<=height; j+=20){
    for (int i=-panX%20-20; i<=width; i+=20) {
        float bruit=noise((i+panX)/30,(j+panY)/30);
      
        fill(255-bruit*100, 255-bruit*100, 255-bruit*100, 180);
        rect(((((j+panY)/20)%2)*10+i), j, 46, 55);
    }
  }
  fill(100,100,100);
  rect(0, 0, 90, 250);
}
 
 
void draw() {  // this is run repeatedly.  
  drawBg();

  textAlign(CENTER, CENTER);
  imageMode(CENTER);
  textSize(50);
  fill(255,25,25);
  
  if (state==2){
     
     fill(0);
     rect(0,0,width,height);
     if (afficherTableau==false){
       fill(255,25,25);
       textSize(100);
       text("GAME", (width/2),height/2 -35);
       text("OVER", (width/2),height/2  +35 );
       textSize(40);
    
       text("Entrez votre nom", width/2, height/3);
       strokeWeight(2);
       stroke(255,25,25);
       line((width/2)-10,(height/3)+60,(width/2)+10,(height/3)+60 );
     
        
      {
        if(keyPressed==true){
          if(key!=ENTER){
            if(key==BACKSPACE){
              if(nom.length()>0){
              /* supprimer derniere lettre   */ nom=nom.substring(0,nom.length()-1);
              delay(100);
              }
            }else{
            if(nom.length()<10)nom+= key;
            delay(100);  
            }
          }else if (placeNom<10){
            placeNom++;
            nomsClassement[placeNom]=nom;
            scoresClassement[placeNom]=score;
            
            delay(100);
            nom="";
            afficherTableau=true;
          }
          
        }
      } 
    }else if (keyPressed){

        afficherTableau=false;
        snakes[0] = new Snake(names[0], 4, width/2, height/2, 0, 255, 0, 10, 0); 
        state = 0;
        
      
  }
    
    
  }else if (state==1){
    fill(0);
    textSize(20);
    text("Appuyer sur une touche pour commencer", width/2, height/2);
    return;
  } else if (state>1){
    drawBg();
    //remplissage de l ecran
    fill(0);
    rect(0,0,((23-state)*((width/2)-70)/20)+76  ,height);
    rect(width-((23-state)*((width/2)-70)/20),0,width,height);
    
    // affichage GAME OVER
    textSize(100);
    fill(255,25,25);
      text("GA", ((23-state)*((width/2)-70)/20),height/2 -35);
      text("OV", ((23-state)*((width/2)-70)/20)+9,height/2  +35 );

    
      text("ME", width-(((23-state)*((width/2)+70))/20)+150,height/2 -35); 
      text("ER", width-(((23-state)*((width/2)+72))/20)+145,height/2   +35 ); 
 


    
    state--;
  } else {
    moveSnake(snakes[0], mouseX+panX, mouseY+panY);
    // si la position du serpent s'approche du bord, on prefere scroller le jeu plutot que de laisser
    // le serpent s'approcher su bord
    Point p = (Point)snakes[0].pos.get(0);
    if (p.x-panX>width-BORDER_SIZE)
      panX = round(p.x-width+BORDER_SIZE);
    if (p.x-panX<BORDER_SIZE)
      panX = round(p.x-BORDER_SIZE);
    if (p.y-panY>height-BORDER_SIZE)
      panY = round(p.y-height+BORDER_SIZE);
    if (p.y-panY<BORDER_SIZE)
      panY = round(p.y-BORDER_SIZE);  
  }
  
  if (snakes[0]!=null && testCollision(snakes[0])){ 
    score=snakes[0].weight-30;
    for (int i=0; i<snakes[0].pos.size(); i++){
        Point m = (Point)snakes[0].pos.get(i);
        m.r = 1;
        if (i%2==0) foods.add(m);
      }
      snakes[0] = null;
      state = 20;
  }
    
  

  // deplace les autres serpents de maniere aleatoire
  for (int k=1; k<snakes.length; k++)
    if (snakes[k]!=null){
      float dx = snakes[k].dirX;
      float dy = snakes[k].dirY;
      float x = ((Point)snakes[k].pos.get(0)).x;
      float y = ((Point)snakes[k].pos.get(0)).y;
      float dd = dist(0,0,x,y);
      
      float ndx = random(10.0, 15)*dx + random(-8,8)*dy; 
      float ndy = random(10.0, 15)*dy - random(-8,8)*dx; 
      if (dd>1000){
        ndx += -x*(dd-1000)/dd;
        ndy += -y*(dd-1000)/dd;
      }
      
      moveSnake(snakes[k], x+ndx, y+ndy);
      if (testCollision(snakes[k])){
        for (int i=0; i<snakes[k].pos.size(); i++){
          Point m = (Point)snakes[k].pos.get(i);
          m.r = 10;
          if (i%2==0) foods.add(m);
        }
        snakes[k] = new Snake(names[k], 4, (int)random(1000), (int)random(1000), 128+64*(k%3), 255*(k%2), 0, 10*cos(k*PI/3), 10*sin(k*PI/3));
      }
    }

  
  
  for (int k=0; k<foods.size(); k++){
    Point f = (Point)foods.get(k);
    pushMatrix();
    translate(f.x-panX, f.y-panY);
    scale(0.1+sqrt(f.r)/5.0);
    //Q4
    if((f.x-panX>90)||(f.y-panY>250)){
      colorMode(HSB,255);
      int var = (frameCount*6)%155;
      fill(k/5+var/2,k/2+var/2,k/2+var/2);
      quad(0,20,10,0,20,20,10,45);
      colorMode(RGB,255);
    }  
    popMatrix();
    
  }
    
  for (int k=0; k<snakes.length; k++){
    if (snakes[k]!=null)
      drawSnake(snakes[k]);
  }
  
  noFill();
  stroke(0, 128);
  //Q3 
  int x=5;
  for(int i =0;i<300;i+=50)
  curve(/**c1**/width/2-BORDER_SIZE , /**c1**/(BORDER_SIZE/2)-i , /**x1**/BORDER_SIZE , /**y1**/BORDER_SIZE-x ,/**x2**/ width-BORDER_SIZE ,/**y2**/ BORDER_SIZE+x , /**c2**/width/2+BORDER_SIZE ,/**c2**/ (BORDER_SIZE*3/2)+i);
  for(int j =0;j<300;j+=50)
  curve(/**xc1**/width-BORDER_SIZE/2+j , /**yc1**/height/2-BORDER_SIZE , /**x1**/width-BORDER_SIZE+x , /**y1**/BORDER_SIZE , /**x2**/width-BORDER_SIZE-x, /**y2**/height-BORDER_SIZE , /**xc2**/width-(BORDER_SIZE*3/2)-j , /**yc2**/height/2+BORDER_SIZE);
  for(int k =0;k<300;k+=50)
  curve(/**xc1**/width/2-BORDER_SIZE,/**yc1**/height-BORDER_SIZE-k,/**x1**/BORDER_SIZE,/**y1**/height-BORDER_SIZE-x,/**x2**/width-BORDER_SIZE,/**y2**/height-BORDER_SIZE+x,/**xc2**/width/2+BORDER_SIZE,/**yc2**/height-BORDER_SIZE+k);
  for(int l =0;l<300;l+=50)
  curve(BORDER_SIZE+l,height/2-BORDER_SIZE,BORDER_SIZE+x,BORDER_SIZE,BORDER_SIZE-x,width-BORDER_SIZE,BORDER_SIZE-l,height/2+BORDER_SIZE);

  // Q9 quadricoptere
  pushMatrix();
  tint(0, 127);
  strokeWeight(5);
  ellipse(width/2-30,height/2-30,20, 20);
  ellipse(width/2+30,height/2+30,20, 20);
  ellipse(width/2-30,height/2+30,20, 20);
  ellipse(width/2+30,height/2-30,20, 20);
  fill(0,0,0,127);
  strokeWeight(1);
  ellipse(width/2,height/2,30,30);
  strokeWeight(5);
  line((width/2)-(sqrt(2)*15/2)-2,(height/2)+(sqrt(2)*15/2)+2,(width/2)-30+(sqrt(2)*10/2)+1,(height/2)+30-(sqrt(2)*10/2)-1);
  line((width/2)+(sqrt(2)*15/2)+2,(height/2)-(sqrt(2)*15/2)-2,(width/2)+30-(sqrt(2)*10/2)-1,(height/2)-30+(sqrt(2)*10/2)+1);
  line((width/2)+(sqrt(2)*15/2)+2,(height/2)+(sqrt(2)*15/2)+2,(width/2)+30-(sqrt(2)*10/2)-1,(height/2)+30-(sqrt(2)*10/2)-1);
  line((width/2)-(sqrt(2)*15/2)-2,(height/2)-(sqrt(2)*15/2)-2,(width/2)-30+(sqrt(2)*10/2)+1,(height/2)-30+(sqrt(2)*10/2)+1);
  strokeWeight(2);
  //Haut Gauche
  curve((width/2)-30-30, (height/2)-30-30,(width/2)-30   ,(height/2)-30-10,(width/2)-30   ,(height/2)-30+10,(width/2)-30+30,(height/2)-30+30);
  curve((width/2)-30+30, (height/2)-30-30,(width/2)-30+10,(height/2)-30   ,(width/2)-30-10,(height/2)-30   ,(width/2)-30-30,(height/2)-30+30);
  //Bas Droite
  curve((width/2)+30+30, (height/2)+30+30,(width/2)+30   ,(height/2)+30+10,(width/2)+30   ,(height/2)+30-10,(width/2)+30-30,(height/2)+30-30);
  curve((width/2)+30-30, (height/2)+30+30,(width/2)+30-10,(height/2)+30   ,(width/2)+30+10,(height/2)+30   ,(width/2)+30+30,(height/2)+30-30);
  //Haut Droite
  curve((width/2)+30-30, (height/2)-30-30,(width/2)+30   ,(height/2)-30-10,(width/2)+30   ,(height/2)-30+10,(width/2)+30+30,(height/2)-30+30);
  curve((width/2)+30+30, (height/2)-30-30,(width/2)+30+10,(height/2)-30   ,(width/2)+30-10,(height/2)-30   ,(width/2)+30-30,(height/2)-30+30);
  //Bas Gauche
  curve((width/2)-30+30, (height/2)+30+30,(width/2)-30   ,(height/2)+30+10,(width/2)-30   ,(height/2)+30-10,(width/2)-30-30,(height/2)+30-30);
  curve((width/2)-30-30, (height/2)+30+30,(width/2)-30-10,(height/2)+30   ,(width/2)-30+10,(height/2)+30   ,(width/2)-30+30,(height/2)+30-30);
  popMatrix();  
  
  //affichage nom pour le tableau de score
  pushMatrix();
  fill(255,25,25);
  textSize(40);
  text(nom,width/2,(height/3)+40);
  popMatrix();
  
 
  //affichage du tableau de score
  if((state==2)&&(afficherTableau)){
    if(keyPressed==false){

    textAlign(LEFT);
    for(int i=0;i<nomsClassement.length-1;i++){
       if(i+1<10) text((i+1)+".   "+nomsClassement[i],10,(height/3)+(35*i));
       if(i+1==10) text((i+1)+". "+nomsClassement[i],10,(height/3)+(35*i));
       text("Score: "+scoresClassement[i],10+10*40, (height/3)+(35*i));
    }textAlign(CENTER);
    text("Appuyez sur une touche pour rejouer",width/2,2*height/3);
    trieTableau();
    scoresClassement[10]=0;
    nomsClassement[10]="";
    }
   
  }

 //fin affichage tableau de score
  

  
  
  
  textSize(10);
  fill(255,255,255);
  if(state==0){
  trieTableauIn();
  scoresClassementIn[0]=snakes[0].score;
  nomsClassementIn[0]=snakes[0].name;
  for(int i=0;i<scoresClassementIn.length; i++){
   text(snakes[i].name+" "+snakes[i].score,35,40*i+20);
 }
}

 for(int k=1;k<snakes.length;k++){
  scoresClassementIn[k]=snakes[k].score;
  nomsClassementIn[k]=snakes[k].name;
 }

 

}//Fin Draw
 
void keyPressed() {
  if (state>0){
    if (state==1){
    snakes[0] = new Snake(names[0], 4, width/2, height/2, 0, 255, 0, 10, 0); 
    state = 0;
    }
  }
  else if(key==' ' && snakes[0].weight>42)
    snakes[0].speed =20;  
}
void keyReleased() {
  if(snakes[0]!=null && key==' ')
    snakes[0].speed =10;
}

void trieTableau(){
  for (int j=0; j<(scoresClassement.length);j++){
    for(int i=0; i<(scoresClassement.length-1);i++){
      if (scoresClassement[i]<scoresClassement[i+1]){
        
        scoresClassementTemp=scoresClassement[i+1];
        nomsClassementTemp=nomsClassement[i+1];
        
        scoresClassement[i+1]=scoresClassement[i];
        nomsClassement[i+1]=nomsClassement[i];
        
        scoresClassement[i]=scoresClassementTemp;
        nomsClassement[i]=nomsClassementTemp;
        
      }
    }
  }
}
void trieTableauIn(){
  for (int j=0; j<(scoresClassementIn.length);j++){
    for(int i=0; i<(scoresClassementIn.length-1);i++){
      if (scoresClassementIn[i]<scoresClassementIn[i+1]){
        
        scoresClassementInTemp=scoresClassementIn[i+1];
        nomsClassementInTemp=nomsClassementIn[i+1];
        
        scoresClassementIn[i+1]=scoresClassementIn[i];
        nomsClassementIn[i+1]=nomsClassementIn[i];
        
        scoresClassementIn[i]=scoresClassementInTemp;
        nomsClassementIn[i]=nomsClassementInTemp;
        
      }
    }
  }
}



 