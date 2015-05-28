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
## @deftypefn {Function File} {@var{retval} =} frst (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: kepuss <kepuss@Odin>
## Created: 2015-03-28

function [retval] = lfsr (k1,k2,k3,l)
  retval = zeros(1,l);
  for i=1:l
    retval(i) = bitxor( bitxor(k1(mod(i-1,length(k1))+1), k2(mod(i-1,length(k2))+1)) , k3(mod(i-1,length(k3))+1));
  endfor
  
endfunction
