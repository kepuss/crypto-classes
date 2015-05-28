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
## @deftypefn {Function File} {@var{retval} =} determinant (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: kepuss <kepuss@Odin>
## Created: 2015-03-24

function [retval] = determinant (i,j,X)
  a1 = X(i) - X(1);
	b1 = X(i+1) - X(2);
	a2 = X(j) - X(1);
	b2 = X(j+1) - X(2);
  r1 = a1*b2
  r2 = a2*b1
  det = a1*b2 - a2*b1;
	retval=  abs(det);
endfunction
