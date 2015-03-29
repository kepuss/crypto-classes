k1 = initKey(19)
k2 = initKey(22)
k3 = initKey(23)

mL = 64;

c = lfsr(k1,k2,k3,mL)
A = buildAMatrix(mL);

result = binaryGauss(A,c);

restoredK1 = result(1:19)
restoredK2 = result(20:41)
restoredK3 = result(42:64)


c=lfsr(k1,k2,k3,30)
c=lfsr(restoredK1,restoredK2,restoredK3,30)