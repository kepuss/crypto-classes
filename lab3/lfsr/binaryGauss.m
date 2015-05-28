## Copyright (C) 2015 kepuss
## 
## This program is free software; you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation; either version 3 of the License, or
## (at your option) any later version.
## 
## This program is distributed in the hope that it will be useful,
## but WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
## 
## You should have received a copy of the GNU General Public License
## along with this program.  If not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*- 
## @deftypefn {Function File} {@var{retval} =} gauss (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: kepuss <kepuss@Odin>
## Created: 2015-03-28

function [X] = binaryGauss (A,b)

switches=zeros(100,2);
counter=1;

for i=1:length(A)
  if A(i,i) != 1
    for j=1 : length(A)
      if A(i,j) ==1 && (A(j,i) ==1 || j>i)
        A(:,[i,j]) = A(:,[j,i]);
        switches(counter,1)=i;
        switches(counter,2)=j;
        counter=counter+1;
 #       b(:,[i,j]) = b(:,[j,i]);
      endif
    endfor 
  endif
endfor


#A(:,[63,41]) = A(:,[41,63]);
#A(:,[64,42]) = A(:,[42,64]);



for i=1:length(A)
  
        for r=1:length(A)
          if A(r,r) !=1
            for s=r+1:length(A)
              if A(r,s) ==1 
            #r
            #s
                A(:,[r,s]) = A(:,[s,r]);
                switches(counter,1)=r;
        switches(counter,2)=s;
        counter=counter+1;
              elseif A(s,r) ==1
                #A([r,s],:) = A([s,r],:);
                #b(:,[r,s]) = b(:,[s,r]);
              endif
            endfor
          endif
        endfor
       
  
  
  for j=i+1:length(A)
    if A(j,i) !=0
      for f =1:length(A) # xor linii
           A(j,f)=bitxor(A(j,f),A(i,f));  
      endfor
        b(j)=bitxor(b(j) , b(i)); # xor b
    endif
   endfor
endfor 

for i=length(A): -1 :1
if A(i,i) == 1
for j=i-1:-1:1
    if A(j,i) !=0
      for f =1:length(A) # xor linii
           A(j,f)=bitxor(A(j,f),A(i,f));  
      endfor
        b(j)=bitxor(b(j) , b(i)); # xor b
    endif
   endfor
endif
endfor  
  
#A([63,62],:) = A([63,62],:);

#switches
for g=counter-1: -1:1
  b(:,[switches(g,1),switches(g,2)]) = b(:,[switches(g,2),switches(g,1)]);
endfor

X=b ;    


endfunction
