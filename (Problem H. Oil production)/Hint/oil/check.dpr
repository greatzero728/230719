{$R+}
program checker;

uses
  testlib, sysutils;

const MAX = 300;

procedure check(x, n : longint);
begin
	if (x < 1) or (x > n) then
		quit(_wa, 'Out of bounds');
end;

var
  p: longint;
  a : array [1..MAX, 1..MAX] of longint;
  s, n, m : longint;
  x1, y1, x2, y2 : longint;
  i, j, ja : longint;
begin
    ja := ans.readlongint;
    p := ouf.readlongint;
    if (ja = -1) and (p = -1) then
    	quit(_ok, 'OK, no solution');
    if (ja <> -1) and (p = -1) then begin
    	quit(_wa, 'Solution not found but exists');
    end;
    x1 := p;
    y1 := ouf.readlongint;
    x2 := ouf.readlongint;
    y2 := ouf.readlongint;
    n := inf.readlongint; 
    m := inf.readlongint; 
    s := inf.readlongint;
    check(x1, n);
	check(x2, n);
	check(y1, m);
	check(y2, m);
	if (x1 = x2) then begin
	  quit(_wa, 'not a rectangle: x1 = x2');
	end;
	if (y1 = y2) then begin
	  quit(_wa, 'not a rectangle: y1 = y2');
	end;
    for i := 1 to n do begin
    	for j := 1 to m do begin
    		a[i][j] := inf.readlongint;
    	end;
    end;
    if s = a[x1][y1] + a[x1][y2] + a[x2][y1] + a[x2][y2] then begin
		if (ja = -1) then begin
			quit(_fail, 'Jury has not found the solution, but the participant has found');
		end;
    	quit(_ok, 'ok');
    end else begin 
    	quit(_wa, 'wrong sum');
    end;
end.
