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
## @deftypefn {Function File} {@var{retval} =} buildAMatrix (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: kepuss <kepuss@Odin>
## Created: 2015-03-28

function [array] = buildAMatrix (n)
  i=0;
  j=0;
  k=0;
  array = zeros(n,n);
  
  for d=1:n
    array(d,i+1)=1;
    array(d,j+20)=1;
    array(d,k+42)=1;
    
    i=mod(i+1,19);
    j=mod(j+1,22);
    k=mod(k+1,23);
  endfor

endfunction
