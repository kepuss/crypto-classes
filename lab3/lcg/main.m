clear;

a=2;
c=5;
p= 3455;
x_init=295075153353;

array = LCG(x_init,a,c,p,20)



detValues=zeros(1,15);
for j=3:18
  detValues(j)=determinant(j,j+1,array);
endfor

detValues=detValues(3:end)
countP=gcd(detValues(1),detValues(2),detValues(3),detValues(4),detValues(5),detValues(6),detValues(7),detValues(8),detValues(9),detValues(10),detValues(11),detValues(12),detValues(13),detValues(14),detValues(15))


sum1 = 0;
sum2 = 0;
array(1)/countP
Xn2=array(4);
Xn1=array(3);
Xn=array(2);

countA = ((Xn1+sum1*countP)-(Xn2 + sum2*countP))/(Xn-Xn1);
#countA = floor(mod(array(2),countP)/array(1));

countC = array(3) - mod(countA*array(2),countP)



arrayGenerated = [array(16),array(17)]
arrayPredicted = LCG(array(15),countA,countC,countP,2)
if arrayGenerated == arrayPredicted 
  printf("test result: true\n");
else
  printf("test result: false\n");
endif